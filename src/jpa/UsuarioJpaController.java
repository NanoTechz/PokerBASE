/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jpa;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import model.Sessao;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import jpa.exceptions.NonexistentEntityException;
import model.Usuario;

/**
 *
 * @author augusto
 */
public class UsuarioJpaController implements Serializable {

    public UsuarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuario usuario) {
        if (usuario.getListaSessao() == null) {
            usuario.setListaSessao(new ArrayList<Sessao>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Sessao> attachedListaSessao = new ArrayList<Sessao>();
            for (Sessao listaSessaoSessaoToAttach : usuario.getListaSessao()) {
                listaSessaoSessaoToAttach = em.getReference(listaSessaoSessaoToAttach.getClass(), listaSessaoSessaoToAttach.getId());
                attachedListaSessao.add(listaSessaoSessaoToAttach);
            }
            usuario.setListaSessao(attachedListaSessao);
            em.persist(usuario);
            for (Sessao listaSessaoSessao : usuario.getListaSessao()) {
                Usuario oldUsuarioOfListaSessaoSessao = listaSessaoSessao.getUsuario();
                listaSessaoSessao.setUsuario(usuario);
                listaSessaoSessao = em.merge(listaSessaoSessao);
                if (oldUsuarioOfListaSessaoSessao != null) {
                    oldUsuarioOfListaSessaoSessao.getListaSessao().remove(listaSessaoSessao);
                    oldUsuarioOfListaSessaoSessao = em.merge(oldUsuarioOfListaSessaoSessao);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuario usuario) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario persistentUsuario = em.find(Usuario.class, usuario.getId());
            List<Sessao> listaSessaoOld = persistentUsuario.getListaSessao();
            List<Sessao> listaSessaoNew = usuario.getListaSessao();
            List<Sessao> attachedListaSessaoNew = new ArrayList<Sessao>();
            for (Sessao listaSessaoNewSessaoToAttach : listaSessaoNew) {
                listaSessaoNewSessaoToAttach = em.getReference(listaSessaoNewSessaoToAttach.getClass(), listaSessaoNewSessaoToAttach.getId());
                attachedListaSessaoNew.add(listaSessaoNewSessaoToAttach);
            }
            listaSessaoNew = attachedListaSessaoNew;
            usuario.setListaSessao(listaSessaoNew);
            usuario = em.merge(usuario);
            for (Sessao listaSessaoOldSessao : listaSessaoOld) {
                if (!listaSessaoNew.contains(listaSessaoOldSessao)) {
                    listaSessaoOldSessao.setUsuario(null);
                    listaSessaoOldSessao = em.merge(listaSessaoOldSessao);
                }
            }
            for (Sessao listaSessaoNewSessao : listaSessaoNew) {
                if (!listaSessaoOld.contains(listaSessaoNewSessao)) {
                    Usuario oldUsuarioOfListaSessaoNewSessao = listaSessaoNewSessao.getUsuario();
                    listaSessaoNewSessao.setUsuario(usuario);
                    listaSessaoNewSessao = em.merge(listaSessaoNewSessao);
                    if (oldUsuarioOfListaSessaoNewSessao != null && !oldUsuarioOfListaSessaoNewSessao.equals(usuario)) {
                        oldUsuarioOfListaSessaoNewSessao.getListaSessao().remove(listaSessaoNewSessao);
                        oldUsuarioOfListaSessaoNewSessao = em.merge(oldUsuarioOfListaSessaoNewSessao);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = usuario.getId();
                if (findUsuario(id) == null) {
                    throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario usuario;
            try {
                usuario = em.getReference(Usuario.class, id);
                usuario.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.", enfe);
            }
            List<Sessao> listaSessao = usuario.getListaSessao();
            for (Sessao listaSessaoSessao : listaSessao) {
                listaSessaoSessao.setUsuario(null);
                listaSessaoSessao = em.merge(listaSessaoSessao);
            }
            em.remove(usuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuario> findUsuarioEntities() {
        return findUsuarioEntities(true, -1, -1);
    }

    public List<Usuario> findUsuarioEntities(int maxResults, int firstResult) {
        return findUsuarioEntities(false, maxResults, firstResult);
    }

    private List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuario.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Usuario findUsuario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuario> rt = cq.from(Usuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
