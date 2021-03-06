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
import model.Operacao;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import jpa.exceptions.NonexistentEntityException;
import model.Bankroll;
import model.Usuario;

/**
 *
 * @author augusto
 */
public class BankrollJpaController implements Serializable {
    
    
    
    public BankrollJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Bankroll bankRoll) {
        if (bankRoll.getListaOperacao() == null) {
            bankRoll.setListaOperacao(new ArrayList<Operacao>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Operacao> attachedListaOperacao = new ArrayList<Operacao>();
            for (Operacao listaOperacaoOperacaoToAttach : bankRoll.getListaOperacao()) {
                listaOperacaoOperacaoToAttach = em.getReference(listaOperacaoOperacaoToAttach.getClass(), listaOperacaoOperacaoToAttach.getId());
                attachedListaOperacao.add(listaOperacaoOperacaoToAttach);
            }
            bankRoll.setListaOperacao(attachedListaOperacao);
            em.persist(bankRoll);
            for (Operacao listaOperacaoOperacao : bankRoll.getListaOperacao()) {
                Bankroll oldBankrollOfListaOperacaoOperacao = listaOperacaoOperacao.getBankroll();
                listaOperacaoOperacao.setBankroll(bankRoll);
                listaOperacaoOperacao = em.merge(listaOperacaoOperacao);
                if (oldBankrollOfListaOperacaoOperacao != null) {
                    oldBankrollOfListaOperacaoOperacao.getListaOperacao().remove(listaOperacaoOperacao);
                    oldBankrollOfListaOperacaoOperacao = em.merge(oldBankrollOfListaOperacaoOperacao);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Bankroll bankRoll) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Bankroll persistentBankRoll = em.find(Bankroll.class, bankRoll.getId());
            List<Operacao> listaOperacaoOld = persistentBankRoll.getListaOperacao();
            List<Operacao> listaOperacaoNew = bankRoll.getListaOperacao();
            List<Operacao> attachedListaOperacaoNew = new ArrayList<Operacao>();
            for (Operacao listaOperacaoNewOperacaoToAttach : listaOperacaoNew) {
                listaOperacaoNewOperacaoToAttach = em.getReference(listaOperacaoNewOperacaoToAttach.getClass(), listaOperacaoNewOperacaoToAttach.getId());
                attachedListaOperacaoNew.add(listaOperacaoNewOperacaoToAttach);
            }
            listaOperacaoNew = attachedListaOperacaoNew;
            bankRoll.setListaOperacao(listaOperacaoNew);
            bankRoll = em.merge(bankRoll);
            for (Operacao listaOperacaoOldOperacao : listaOperacaoOld) {
                if (!listaOperacaoNew.contains(listaOperacaoOldOperacao)) {
                    listaOperacaoOldOperacao.setBankroll(null);
                    listaOperacaoOldOperacao = em.merge(listaOperacaoOldOperacao);
                }
            }
            for (Operacao listaOperacaoNewOperacao : listaOperacaoNew) {
                if (!listaOperacaoOld.contains(listaOperacaoNewOperacao)) {
                    Bankroll oldBankrollOfListaOperacaoNewOperacao = listaOperacaoNewOperacao.getBankroll();
                    listaOperacaoNewOperacao.setBankroll(bankRoll);
                    listaOperacaoNewOperacao = em.merge(listaOperacaoNewOperacao);
                    if (oldBankrollOfListaOperacaoNewOperacao != null && !oldBankrollOfListaOperacaoNewOperacao.equals(bankRoll)) {
                        oldBankrollOfListaOperacaoNewOperacao.getListaOperacao().remove(listaOperacaoNewOperacao);
                        oldBankrollOfListaOperacaoNewOperacao = em.merge(oldBankrollOfListaOperacaoNewOperacao);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = bankRoll.getId();
                if (findBankRoll(id) == null) {
                    throw new NonexistentEntityException("The bankroll with id " + id + " no longer exists.");
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
            Bankroll bankRoll;
            try {
                bankRoll = em.getReference(Bankroll.class, id);
                bankRoll.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The bankRoll with id " + id + " no longer exists.", enfe);
            }
            List<Operacao> listaOperacao = bankRoll.getListaOperacao();
            for (Operacao listaOperacaoOperacao : listaOperacao) {
                listaOperacaoOperacao.setBankroll(null);
                listaOperacaoOperacao = em.merge(listaOperacaoOperacao);
            }
            em.remove(bankRoll);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Bankroll> findBankRollEntities() {
        return findBankRollEntities(true, -1, -1);
    }

    public List<Bankroll> findBankRollEntities(int maxResults, int firstResult) {
        return findBankRollEntities(false, maxResults, firstResult);
    }

    private List<Bankroll> findBankRollEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Bankroll.class));
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

    public Bankroll findBankRoll(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Bankroll.class, id);
        } finally {
            em.close();
        }
    }

    public List<Bankroll> findBankRollUsuario(Usuario usuario) {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createQuery("select b from Bankroll b where b.usuario = :usuario", Bankroll.class);
            query.setParameter("usuario", usuario);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return new ArrayList<Bankroll>();
        } finally {
            em.close();
        }
    }

    public int getBankRollCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Bankroll> rt = cq.from(Bankroll.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
