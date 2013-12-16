/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import jpa.exceptions.NonexistentEntityException;
import model.Cash;
import model.Sala;
import model.Sessao;
import model.Usuario;
import model.simples.CashSimples;

/**
 *
 * @author augusto
 */
public class CashJpaController implements Serializable {

    public List<Cash> findCashUsuario(Usuario usuario) {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createQuery("select new Cash(b.limite, b.valorBlind, sum(b.valorGanho), sum(b.duracaoHoras), sum(b.numeroMaos)) from Cash b where b.sessao.usuario = :usuario GROUP BY b.limite ORDER BY b.valorBlind ASC", Cash.class);

            query.setParameter("usuario", usuario);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return new ArrayList<Cash>();
        } finally {
            em.close();
        }
    }

    public List<CashSimples> findCashGroupByLimite(Usuario usuario) {
          EntityManager em = getEntityManager();
        try {
            Query query = em.createQuery("select new model.simples.CashSimples(b.limite, sum(b.numeroMaos), sum(b.valorGanho)) from Cash b where b.sessao.usuario = :usuario GROUP BY b.limite ORDER BY b.valorBlind ASC");

            query.setParameter("usuario", usuario);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return new ArrayList<CashSimples>();
        } finally {
            em.close();
        }
    }

    public CashJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cash cash) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sala sala = cash.getSala();
            if (sala != null) {
                sala = em.getReference(sala.getClass(), sala.getId());
                cash.setSala(sala);
            }
            Sessao sessao = cash.getSessao();
            if (sessao != null) {
                sessao = em.getReference(sessao.getClass(), sessao.getId());
                cash.setSessao(sessao);
            }
            em.persist(cash);
            if (sala != null) {
                sala.getListaCash().add(cash);
                sala = em.merge(sala);
            }
            if (sessao != null) {
                sessao.getListaCash().add(cash);
                sessao = em.merge(sessao);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cash cash) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cash persistentCash = em.find(Cash.class, cash.getId());
            Sala salaOld = persistentCash.getSala();
            Sala salaNew = cash.getSala();
            Sessao sessaoOld = persistentCash.getSessao();
            Sessao sessaoNew = cash.getSessao();
            if (salaNew != null) {
                salaNew = em.getReference(salaNew.getClass(), salaNew.getId());
                cash.setSala(salaNew);
            }
            if (sessaoNew != null) {
                sessaoNew = em.getReference(sessaoNew.getClass(), sessaoNew.getId());
                cash.setSessao(sessaoNew);
            }
            cash = em.merge(cash);
            if (salaOld != null && !salaOld.equals(salaNew)) {
                salaOld.getListaCash().remove(cash);
                salaOld = em.merge(salaOld);
            }
            if (salaNew != null && !salaNew.equals(salaOld)) {
                salaNew.getListaCash().add(cash);
                salaNew = em.merge(salaNew);
            }
            if (sessaoOld != null && !sessaoOld.equals(sessaoNew)) {
                sessaoOld.getListaCash().remove(cash);
                sessaoOld = em.merge(sessaoOld);
            }
            if (sessaoNew != null && !sessaoNew.equals(sessaoOld)) {
                sessaoNew.getListaCash().add(cash);
                sessaoNew = em.merge(sessaoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cash.getId();
                if (findCash(id) == null) {
                    throw new NonexistentEntityException("The cash with id " + id + " no longer exists.");
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
            Cash cash;
            try {
                cash = em.getReference(Cash.class, id);
                cash.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cash with id " + id + " no longer exists.", enfe);
            }
            Sala sala = cash.getSala();
            if (sala != null) {
                sala.getListaCash().remove(cash);
                sala = em.merge(sala);
            }
            Sessao sessao = cash.getSessao();
            if (sessao != null) {
                sessao.getListaCash().remove(cash);
                sessao = em.merge(sessao);
            }
            em.remove(cash);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cash> findCashEntities() {
        return findCashEntities(true, -1, -1);
    }

    public List<Cash> findCashEntities(int maxResults, int firstResult) {
        return findCashEntities(false, maxResults, firstResult);
    }

    private List<Cash> findCashEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cash.class));
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

    public Cash findCash(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cash.class, id);
        } finally {
            em.close();
        }
    }

    public int getCashCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cash> rt = cq.from(Cash.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
