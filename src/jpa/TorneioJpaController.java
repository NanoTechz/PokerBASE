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
import model.Sessao;
import model.Sala;
import model.Torneio;
import model.Usuario;
import model.auxiliar.TipoTorneio;
import model.simples.TorneioSimples;

/**
 *
 * @author augusto
 */
public class TorneioJpaController implements Serializable {

    public TorneioSimples getTotal(Usuario usuario) {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createQuery("select new model.simples.TorneioSimples(sum(t.valorGanho), sum(t.buyIn)) from Torneio t where t.sessao.usuario= :usuario");

            query.setParameter("usuario", usuario);
            return (TorneioSimples)query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        } finally {
            em.close();
        }
        
        return null;
    }
    
    public List<TorneioSimples> findTorneiosSimples(Usuario usuario) {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createQuery("select new model.simples.TorneioSimples(t.valorGanho, t.buyIn) from Torneio t where t.sessao.usuario= :usuario ");

            query.setParameter("usuario", usuario);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        } finally {
            em.close();
        }
        
        return null;
    }

    public List<Torneio> findTorneioUsuarioPorBuyIN(Usuario usuario, TipoTorneio tipo) {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createQuery("select new Torneio(t.buyIn, t.tipo, t.genero, sum(t.valorGanho), count(t.id)) from Torneio t where (t.sessao.usuario = :usuario  and t.tipo = :tipo) GROUP BY t.buyIn ORDER BY t.buyIn ASC", Torneio.class);

            query.setParameter("usuario", usuario);
            query.setParameter("tipo", tipo.getValor());

            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return new ArrayList<Torneio>();
        } finally {
            em.close();
        }
    }

    public List<Torneio> findTorneioUsuario(Usuario usuario) {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createQuery("select b from Torneio b where b.sessao.usuario = :usuario", Torneio.class);
            query.setParameter("usuario", usuario);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return new ArrayList<Torneio>();
        } finally {
            em.close();
        }
    }

    public double totalBuyIn(Usuario usuario) {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createQuery("select SUM(b.buyIn) from Torneio b where b.sessao.usuario = :usuario");
            query.setParameter("usuario", usuario);

            return ((Number) query.getSingleResult()).doubleValue();
        } catch (Exception e) {
            System.out.println("*****************************5*****************************");
            e.printStackTrace(System.out);
            return 0;
        } finally {
            em.close();
        }
    }

    public double totalValorGanho(Usuario usuario) {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createQuery("select sum(b.valorGanho) from Torneio b where b.sessao.usuario = :usuario");
            query.setParameter("usuario", usuario);
            return ((Number) query.getSingleResult()).doubleValue();
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return 0;
        } finally {
            em.close();
        }
    }

    public TorneioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Torneio torneio) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sessao sessao = torneio.getSessao();
            if (sessao != null) {
                sessao = em.getReference(sessao.getClass(), sessao.getId());
                torneio.setSessao(sessao);
            }
            Sala sala = torneio.getSala();
            if (sala != null) {
                sala = em.getReference(sala.getClass(), sala.getId());
                torneio.setSala(sala);
            }
            em.persist(torneio);
            if (sessao != null) {
                sessao.getListaTorneios().add(torneio);
                sessao = em.merge(sessao);
            }
            if (sala != null) {
                sala.getListaTorneios().add(torneio);
                sala = em.merge(sala);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Torneio torneio) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Torneio persistentTorneio = em.find(Torneio.class, torneio.getId());
            Sessao sessaoOld = persistentTorneio.getSessao();
            Sessao sessaoNew = torneio.getSessao();
            Sala salaOld = persistentTorneio.getSala();
            Sala salaNew = torneio.getSala();
            if (sessaoNew != null) {
                sessaoNew = em.getReference(sessaoNew.getClass(), sessaoNew.getId());
                torneio.setSessao(sessaoNew);
            }
            if (salaNew != null) {
                salaNew = em.getReference(salaNew.getClass(), salaNew.getId());
                torneio.setSala(salaNew);
            }
            torneio = em.merge(torneio);
            if (sessaoOld != null && !sessaoOld.equals(sessaoNew)) {
                sessaoOld.getListaTorneios().remove(torneio);
                sessaoOld = em.merge(sessaoOld);
            }
            if (sessaoNew != null && !sessaoNew.equals(sessaoOld)) {
                sessaoNew.getListaTorneios().add(torneio);
                sessaoNew = em.merge(sessaoNew);
            }
            if (salaOld != null && !salaOld.equals(salaNew)) {
                salaOld.getListaTorneios().remove(torneio);
                salaOld = em.merge(salaOld);
            }
            if (salaNew != null && !salaNew.equals(salaOld)) {
                salaNew.getListaTorneios().add(torneio);
                salaNew = em.merge(salaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = torneio.getId();
                if (findTorneio(id) == null) {
                    throw new NonexistentEntityException("The torneio with id " + id + " no longer exists.");
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
            Torneio torneio;
            try {
                torneio = em.getReference(Torneio.class, id);
                torneio.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The torneio with id " + id + " no longer exists.", enfe);
            }
            Sessao sessao = torneio.getSessao();
            if (sessao != null) {
                sessao.getListaTorneios().remove(torneio);
                sessao = em.merge(sessao);
            }
            Sala sala = torneio.getSala();
            if (sala != null) {
                sala.getListaTorneios().remove(torneio);
                sala = em.merge(sala);
            }
            em.remove(torneio);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Torneio> findTorneioEntities() {
        return findTorneioEntities(true, -1, -1);
    }

    public List<Torneio> findTorneioEntities(int maxResults, int firstResult) {
        return findTorneioEntities(false, maxResults, firstResult);
    }

    private List<Torneio> findTorneioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Torneio.class));
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

    public Torneio findTorneio(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Torneio.class, id);
        } finally {
            em.close();
        }
    }

    public int getTorneioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Torneio> rt = cq.from(Torneio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
