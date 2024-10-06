/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
    package CONTROLADORES;

import CapaLogica_ventas.DetalleVentas;
import java.io.Serializable;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class DetalleVentasJpaController implements Serializable {

    private EntityManagerFactory emf = null;

    public DetalleVentasJpaController(EntityManagerFactory emf) {
        this.emf = Persistence.createEntityManagerFactory("CapaLogica_ventas_jar_1.0-SNAPSHOTPU");
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(DetalleVentas detalleVentas) throws Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            EntityTransaction et = em.getTransaction();
            et.begin();
            em.persist(detalleVentas);
            et.commit();
        } catch (Exception ex) {
            if (findDetalleVentas(detalleVentas.getIddetalleventas()) != null) {
                throw new Exception("DetalleVentas " + detalleVentas + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(DetalleVentas detalleVentas) throws Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            EntityTransaction et = em.getTransaction();
            et.begin();
            detalleVentas = em.merge(detalleVentas);
            et.commit();
        } catch (Exception ex) {
            Integer id = detalleVentas.getIddetalleventas();
            if (findDetalleVentas(id) == null) {
                throw new Exception("The detalleVentas with id " + id + " no longer exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            EntityTransaction et = em.getTransaction();
            et.begin();
            DetalleVentas detalleVentas;
            try {
                detalleVentas = em.getReference(DetalleVentas.class, id);
                detalleVentas.getIddetalleventas(); // Trigger the exception if the id does not exist
            } catch (Exception enfe) {
                throw new Exception("The detalleVentas with id " + id + " no longer exists.", enfe);
            }
            em.remove(detalleVentas);
            et.commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<DetalleVentas> findDetalleVentasEntities() {
        return findDetalleVentasEntities(true, -1, -1);
    }

    public List<DetalleVentas> findDetalleVentasEntities(int maxResults, int firstResult) {
        return findDetalleVentasEntities(false, maxResults, firstResult);
    }

    private List<DetalleVentas> findDetalleVentasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<DetalleVentas> q = em.createNamedQuery("DetalleVentas.findAll", DetalleVentas.class);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public DetalleVentas findDetalleVentas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DetalleVentas.class, id);
        } finally {
            em.close();
        }
    }

    public int getDetalleVentasCount() {
        EntityManager em = getEntityManager();
        try {
            javax.persistence.criteria.CriteriaQuery<Long> cq = em.getCriteriaBuilder().createQuery(Long.class);
            javax.persistence.criteria.Root<DetalleVentas> rt = cq.from(DetalleVentas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            javax.persistence.Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
}
