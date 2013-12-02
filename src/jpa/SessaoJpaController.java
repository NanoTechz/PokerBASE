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
import model.Usuario;
import model.Cash;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import jpa.exceptions.NonexistentEntityException;
import model.BankRoll;
import model.Sessao;
import model.Torneio;

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
        if (sessao.getListaCash() == null) {
            sessao.setListaCash(new ArrayList<Cash>());
        }
        if (sessao.getListaTorneios() == null) {
            sessao.setListaTorneios(new ArrayList<Torneio>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario usuario = sessao.getUsuario();
            if (usuario != null) {
                usuario = em.getReference(usuario.getClass(), usuario.getId());
                sessao.setUsuario(usuario);
            }
            List<Cash> attachedListaCash = new ArrayList<Cash>();
            for (Cash listaCashCashToAttach : sessao.getListaCash()) {
                listaCashCashToAttach = em.getReference(listaCashCashToAttach.getClass(), listaCashCashToAttach.getId());
                attachedListaCash.add(listaCashCashToAttach);
            }
            sessao.setListaCash(attachedListaCash);
            List<Torneio> attachedListaTorneios = new ArrayList<Torneio>();
            for (Torneio listaTorneiosTorneioToAttach : sessao.getListaTorneios()) {
                listaTorneiosTorneioToAttach = em.getReference(listaTorneiosTorneioToAttach.getClass(), listaTorneiosTorneioToAttach.getId());
                attachedListaTorneios.add(listaTorneiosTorneioToAttach);
            }
            sessao.setListaTorneios(attachedListaTorneios);
            em.persist(sessao);
            if (usuario != null) {
                usuario.getListaSessao().add(sessao);
                usuario = em.merge(usuario);
            }
            for (Cash listaCashCash : sessao.getListaCash()) {
                Sessao oldSessaoOfListaCashCash = listaCashCash.getSessao();
                listaCashCash.setSessao(sessao);
                listaCashCash = em.merge(listaCashCash);
                if (oldSessaoOfListaCashCash != null) {
                    oldSessaoOfListaCashCash.getListaCash().remove(listaCashCash);
                    oldSessaoOfListaCashCash = em.merge(oldSessaoOfListaCashCash);
                }
            }
            for (Torneio listaTorneiosTorneio : sessao.getListaTorneios()) {
                Sessao oldSessaoOfListaTorneiosTorneio = listaTorneiosTorneio.getSessao();
                listaTorneiosTorneio.setSessao(sessao);
                listaTorneiosTorneio = em.merge(listaTorneiosTorneio);
                if (oldSessaoOfListaTorneiosTorneio != null) {
                    oldSessaoOfListaTorneiosTorneio.getListaTorneios().remove(listaTorneiosTorneio);
                    oldSessaoOfListaTorneiosTorneio = em.merge(oldSessaoOfListaTorneiosTorneio);
                }
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
            List<Cash> listaCashOld = persistentSessao.getListaCash();
            List<Cash> listaCashNew = sessao.getListaCash();
            List<Torneio> listaTorneiosOld = persistentSessao.getListaTorneios();
            List<Torneio> listaTorneiosNew = sessao.getListaTorneios();
            if (usuarioNew != null) {
                usuarioNew = em.getReference(usuarioNew.getClass(), usuarioNew.getId());
                sessao.setUsuario(usuarioNew);
            }
            List<Cash> attachedListaCashNew = new ArrayList<Cash>();
            for (Cash listaCashNewCashToAttach : listaCashNew) {
                listaCashNewCashToAttach = em.getReference(listaCashNewCashToAttach.getClass(), listaCashNewCashToAttach.getId());
                attachedListaCashNew.add(listaCashNewCashToAttach);
            }
            listaCashNew = attachedListaCashNew;
            sessao.setListaCash(listaCashNew);
            List<Torneio> attachedListaTorneiosNew = new ArrayList<Torneio>();
            for (Torneio listaTorneiosNewTorneioToAttach : listaTorneiosNew) {
                listaTorneiosNewTorneioToAttach = em.getReference(listaTorneiosNewTorneioToAttach.getClass(), listaTorneiosNewTorneioToAttach.getId());
                attachedListaTorneiosNew.add(listaTorneiosNewTorneioToAttach);
            }
            listaTorneiosNew = attachedListaTorneiosNew;
            sessao.setListaTorneios(listaTorneiosNew);
            sessao = em.merge(sessao);
            if (usuarioOld != null && !usuarioOld.equals(usuarioNew)) {
                usuarioOld.getListaSessao().remove(sessao);
                usuarioOld = em.merge(usuarioOld);
            }
            if (usuarioNew != null && !usuarioNew.equals(usuarioOld)) {
                usuarioNew.getListaSessao().add(sessao);
                usuarioNew = em.merge(usuarioNew);
            }
            for (Cash listaCashOldCash : listaCashOld) {
                if (!listaCashNew.contains(listaCashOldCash)) {
                    listaCashOldCash.setSessao(null);
                    listaCashOldCash = em.merge(listaCashOldCash);
                }
            }
            for (Cash listaCashNewCash : listaCashNew) {
                if (!listaCashOld.contains(listaCashNewCash)) {
                    Sessao oldSessaoOfListaCashNewCash = listaCashNewCash.getSessao();
                    listaCashNewCash.setSessao(sessao);
                    listaCashNewCash = em.merge(listaCashNewCash);
                    if (oldSessaoOfListaCashNewCash != null && !oldSessaoOfListaCashNewCash.equals(sessao)) {
                        oldSessaoOfListaCashNewCash.getListaCash().remove(listaCashNewCash);
                        oldSessaoOfListaCashNewCash = em.merge(oldSessaoOfListaCashNewCash);
                    }
                }
            }
            for (Torneio listaTorneiosOldTorneio : listaTorneiosOld) {
                if (!listaTorneiosNew.contains(listaTorneiosOldTorneio)) {
                    listaTorneiosOldTorneio.setSessao(null);
                    listaTorneiosOldTorneio = em.merge(listaTorneiosOldTorneio);
                }
            }
            for (Torneio listaTorneiosNewTorneio : listaTorneiosNew) {
                if (!listaTorneiosOld.contains(listaTorneiosNewTorneio)) {
                    Sessao oldSessaoOfListaTorneiosNewTorneio = listaTorneiosNewTorneio.getSessao();
                    listaTorneiosNewTorneio.setSessao(sessao);
                    listaTorneiosNewTorneio = em.merge(listaTorneiosNewTorneio);
                    if (oldSessaoOfListaTorneiosNewTorneio != null && !oldSessaoOfListaTorneiosNewTorneio.equals(sessao)) {
                        oldSessaoOfListaTorneiosNewTorneio.getListaTorneios().remove(listaTorneiosNewTorneio);
                        oldSessaoOfListaTorneiosNewTorneio = em.merge(oldSessaoOfListaTorneiosNewTorneio);
                    }
                }
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
            List<Cash> listaCash = sessao.getListaCash();
            for (Cash listaCashCash : listaCash) {
                listaCashCash.setSessao(null);
                listaCashCash = em.merge(listaCashCash);
            }
            List<Torneio> listaTorneios = sessao.getListaTorneios();
            for (Torneio listaTorneiosTorneio : listaTorneios) {
                listaTorneiosTorneio.setSessao(null);
                listaTorneiosTorneio = em.merge(listaTorneiosTorneio);
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
    
      public List<Sessao> findSessaoUsuario(Usuario usuario) {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createQuery("select s from Sessao s where s.usuario = :usuario", Sessao.class);
            query.setParameter("usuario", usuario);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return new ArrayList<Sessao>();
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
