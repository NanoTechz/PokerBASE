/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jpa;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import jpa.exceptions.NonexistentEntityException;
import model.Sessao;
import model.Usuario;

/**
 *
 * @author augusto
 */
public class SessaoJpaController implements Serializable {

    public SessaoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Sessao sessao) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario usuario = sessao.getUsuario();
            if (usuario != null) {
                usuario = em.getReference(usuario.getClass(), usuario.getId());
                sessao.setUsuario(usuario);
            }
            em.persist(sessao);
            if (usuario != null) {
                usuario.getListaSessao().add(sessao);
                usuario = em.merge(usuario);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Sessao sessao) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sessao persistentSessao = em.find(Sessao.class, sessao.getId());
            Usuario usuarioOld = persistentSessao.getUsuario();
            Usuario usuarioNew = sessao.getUsuario();
            if (usuarioNew != null) {
                usuarioNew = em.getReference(usuarioNew.getClass(), usuarioNew.getId());
                sessao.setUsuario(usuarioNew);
            }
            sessao = em.merge(sessao);
            if (usuarioOld != null && !usuarioOld.equals(usuarioNew)) {
                usuarioOld.getListaSessao().remove(sessao);
                usuarioOld = em.merge(usuarioOld);
            }
            if (usuarioNew != null && !usuarioNew.equals(usuarioOld)) {
                usuarioNew.getListaSessao().add(sessao);
                usuarioNew = em.merge(usuarioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = sessao.getId();
                if (findSessao(id) == null) {
                    throw new NonexistentEntityException("The sessao with id " + id + " no longer exists.");
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
            Sessao sessao;
            try {
                sessao = em.getReference(Sessao.class, id);
                sessao.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sessao with id " + id + " no longer exists.", enfe);
            }
            Usuario usuario = sessao.getUsuario();
            if (usuario != null) {
                usuario.getListaSessao().remove(sessao);
                usuario = em.merge(usuario);
            }
            em.remove(sessao);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Sessao> findSessaoEntities() {
        return findSessaoEntities(true, -1, -1);
    }

    public List<Sessao> findSessaoEntities(int maxResults, int firstResult) {
        return findSessaoEntities(false, maxResults, firstResult);
    }

    private List<Sessao> findSessaoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Sessao.class));
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

    public Sessao findSessao(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Sessao.class, id);
        } finally {
            em.close();
        }
    }

    public int getSessaoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Sessao> rt = cq.from(Sessao.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
