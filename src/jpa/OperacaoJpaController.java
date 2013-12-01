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
import model.BankRoll;
import model.Operacao;

/**
 *
 * @author augusto
 */
public class OperacaoJpaController implements Serializable {

    public OperacaoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Operacao operacao) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            BankRoll bankroll = operacao.getBankroll();
            if (bankroll != null) {
                bankroll = em.getReference(bankroll.getClass(), bankroll.getId());
                operacao.setBankroll(bankroll);
            }
            em.persist(operacao);
            if (bankroll != null) {
                bankroll.getListaOperacao().add(operacao);
                bankroll = em.merge(bankroll);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Operacao operacao) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Operacao persistentOperacao = em.find(Operacao.class, operacao.getId());
            BankRoll bankrollOld = persistentOperacao.getBankroll();
            BankRoll bankrollNew = operacao.getBankroll();
            if (bankrollNew != null) {
                bankrollNew = em.getReference(bankrollNew.getClass(), bankrollNew.getId());
                operacao.setBankroll(bankrollNew);
            }
            operacao = em.merge(operacao);
            if (bankrollOld != null && !bankrollOld.equals(bankrollNew)) {
                bankrollOld.getListaOperacao().remove(operacao);
                bankrollOld = em.merge(bankrollOld);
            }
            if (bankrollNew != null && !bankrollNew.equals(bankrollOld)) {
                bankrollNew.getListaOperacao().add(operacao);
                bankrollNew = em.merge(bankrollNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = operacao.getId();
                if (findOperacao(id) == null) {
                    throw new NonexistentEntityException("The operacao with id " + id + " no longer exists.");
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
            Operacao operacao;
            try {
                operacao = em.getReference(Operacao.class, id);
                operacao.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The operacao with id " + id + " no longer exists.", enfe);
            }
            BankRoll bankroll = operacao.getBankroll();
            if (bankroll != null) {
                bankroll.getListaOperacao().remove(operacao);
                bankroll = em.merge(bankroll);
            }
            em.remove(operacao);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Operacao> findOperacaoEntities() {
        return findOperacaoEntities(true, -1, -1);
    }

    public List<Operacao> findOperacaoEntities(int maxResults, int firstResult) {
        return findOperacaoEntities(false, maxResults, firstResult);
    }

    private List<Operacao> findOperacaoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Operacao.class));
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

    public Operacao findOperacao(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Operacao.class, id);
        } finally {
            em.close();
        }
    }

    public int getOperacaoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Operacao> rt = cq.from(Operacao.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
