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
import model.Cash;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import jpa.exceptions.NonexistentEntityException;
import model.Sala;
import model.Torneio;

/**
 *
 * @author augusto
 */
public class SalaJpaController implements Serializable {

    public SalaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Sala sala) {
        if (sala.getListaCash() == null) {
            sala.setListaCash(new ArrayList<Cash>());
        }
        if (sala.getListaTorneios() == null) {
            sala.setListaTorneios(new ArrayList<Torneio>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Cash> attachedListaCash = new ArrayList<Cash>();
            for (Cash listaCashCashToAttach : sala.getListaCash()) {
                listaCashCashToAttach = em.getReference(listaCashCashToAttach.getClass(), listaCashCashToAttach.getId());
                attachedListaCash.add(listaCashCashToAttach);
            }
            sala.setListaCash(attachedListaCash);
            List<Torneio> attachedListaTorneios = new ArrayList<Torneio>();
            for (Torneio listaTorneiosTorneioToAttach : sala.getListaTorneios()) {
                listaTorneiosTorneioToAttach = em.getReference(listaTorneiosTorneioToAttach.getClass(), listaTorneiosTorneioToAttach.getId());
                attachedListaTorneios.add(listaTorneiosTorneioToAttach);
            }
            sala.setListaTorneios(attachedListaTorneios);
            em.persist(sala);
            for (Cash listaCashCash : sala.getListaCash()) {
                Sala oldSalaOfListaCashCash = listaCashCash.getSala();
                listaCashCash.setSala(sala);
                listaCashCash = em.merge(listaCashCash);
                if (oldSalaOfListaCashCash != null) {
                    oldSalaOfListaCashCash.getListaCash().remove(listaCashCash);
                    oldSalaOfListaCashCash = em.merge(oldSalaOfListaCashCash);
                }
            }
            for (Torneio listaTorneiosTorneio : sala.getListaTorneios()) {
                Sala oldSalaOfListaTorneiosTorneio = listaTorneiosTorneio.getSala();
                listaTorneiosTorneio.setSala(sala);
                listaTorneiosTorneio = em.merge(listaTorneiosTorneio);
                if (oldSalaOfListaTorneiosTorneio != null) {
                    oldSalaOfListaTorneiosTorneio.getListaTorneios().remove(listaTorneiosTorneio);
                    oldSalaOfListaTorneiosTorneio = em.merge(oldSalaOfListaTorneiosTorneio);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Sala sala) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sala persistentSala = em.find(Sala.class, sala.getId());
            List<Cash> listaCashOld = persistentSala.getListaCash();
            List<Cash> listaCashNew = sala.getListaCash();
            List<Torneio> listaTorneiosOld = persistentSala.getListaTorneios();
            List<Torneio> listaTorneiosNew = sala.getListaTorneios();
            List<Cash> attachedListaCashNew = new ArrayList<Cash>();
            for (Cash listaCashNewCashToAttach : listaCashNew) {
                listaCashNewCashToAttach = em.getReference(listaCashNewCashToAttach.getClass(), listaCashNewCashToAttach.getId());
                attachedListaCashNew.add(listaCashNewCashToAttach);
            }
            listaCashNew = attachedListaCashNew;
            sala.setListaCash(listaCashNew);
            List<Torneio> attachedListaTorneiosNew = new ArrayList<Torneio>();
            for (Torneio listaTorneiosNewTorneioToAttach : listaTorneiosNew) {
                listaTorneiosNewTorneioToAttach = em.getReference(listaTorneiosNewTorneioToAttach.getClass(), listaTorneiosNewTorneioToAttach.getId());
                attachedListaTorneiosNew.add(listaTorneiosNewTorneioToAttach);
            }
            listaTorneiosNew = attachedListaTorneiosNew;
            sala.setListaTorneios(listaTorneiosNew);
            sala = em.merge(sala);
            for (Cash listaCashOldCash : listaCashOld) {
                if (!listaCashNew.contains(listaCashOldCash)) {
                    listaCashOldCash.setSala(null);
                    listaCashOldCash = em.merge(listaCashOldCash);
                }
            }
            for (Cash listaCashNewCash : listaCashNew) {
                if (!listaCashOld.contains(listaCashNewCash)) {
                    Sala oldSalaOfListaCashNewCash = listaCashNewCash.getSala();
                    listaCashNewCash.setSala(sala);
                    listaCashNewCash = em.merge(listaCashNewCash);
                    if (oldSalaOfListaCashNewCash != null && !oldSalaOfListaCashNewCash.equals(sala)) {
                        oldSalaOfListaCashNewCash.getListaCash().remove(listaCashNewCash);
                        oldSalaOfListaCashNewCash = em.merge(oldSalaOfListaCashNewCash);
                    }
                }
            }
            for (Torneio listaTorneiosOldTorneio : listaTorneiosOld) {
                if (!listaTorneiosNew.contains(listaTorneiosOldTorneio)) {
                    listaTorneiosOldTorneio.setSala(null);
                    listaTorneiosOldTorneio = em.merge(listaTorneiosOldTorneio);
                }
            }
            for (Torneio listaTorneiosNewTorneio : listaTorneiosNew) {
                if (!listaTorneiosOld.contains(listaTorneiosNewTorneio)) {
                    Sala oldSalaOfListaTorneiosNewTorneio = listaTorneiosNewTorneio.getSala();
                    listaTorneiosNewTorneio.setSala(sala);
                    listaTorneiosNewTorneio = em.merge(listaTorneiosNewTorneio);
                    if (oldSalaOfListaTorneiosNewTorneio != null && !oldSalaOfListaTorneiosNewTorneio.equals(sala)) {
                        oldSalaOfListaTorneiosNewTorneio.getListaTorneios().remove(listaTorneiosNewTorneio);
                        oldSalaOfListaTorneiosNewTorneio = em.merge(oldSalaOfListaTorneiosNewTorneio);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = sala.getId();
                if (findSala(id) == null) {
                    throw new NonexistentEntityException("The sala with id " + id + " no longer exists.");
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
            Sala sala;
            try {
                sala = em.getReference(Sala.class, id);
                sala.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sala with id " + id + " no longer exists.", enfe);
            }
            List<Cash> listaCash = sala.getListaCash();
            for (Cash listaCashCash : listaCash) {
                listaCashCash.setSala(null);
                listaCashCash = em.merge(listaCashCash);
            }
            List<Torneio> listaTorneios = sala.getListaTorneios();
            for (Torneio listaTorneiosTorneio : listaTorneios) {
                listaTorneiosTorneio.setSala(null);
                listaTorneiosTorneio = em.merge(listaTorneiosTorneio);
            }
            em.remove(sala);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Sala> findSalaEntities() {
        return findSalaEntities(true, -1, -1);
    }

    public List<Sala> findSalaEntities(int maxResults, int firstResult) {
        return findSalaEntities(false, maxResults, firstResult);
    }

    private List<Sala> findSalaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Sala.class));
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

    public Sala findSala(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Sala.class, id);
        } finally {
            em.close();
        }
    }

    public int getSalaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Sala> rt = cq.from(Sala.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
