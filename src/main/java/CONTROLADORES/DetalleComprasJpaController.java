/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CONTROLADORES;

import CapaLogica_ventas.Compras;
import CapaLogica_ventas.DetalleCompras;
import CapaLogica_ventas.Producto;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author David
 */


import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class DetalleComprasJpaController implements Serializable {

    private EntityManagerFactory emf = null;

    public DetalleComprasJpaController(EntityManagerFactory emf) {
        this.emf = emf
                
                ;
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
        
    }
    

    public void create(DetalleCompras detalleCompras) throws Exception {
    EntityManager em = null;

    try {
        em = getEntityManager();
        em.getTransaction().begin();

        // Establecer la relación con Compras
        Compras idcompra = detalleCompras.getIdcompra();
        if (idcompra != null) {
            detalleCompras.setIdcompra(em.merge(idcompra)); // Merges only if needed
        }

        // Establecer la relación con Producto
        Producto idproducto = detalleCompras.getIdproducto();
        if (idproducto != null) {
            detalleCompras.setIdproducto(em.merge(idproducto)); // Merges only if needed
        }

        em.persist(detalleCompras);
        em.getTransaction().commit();
    } catch (Exception ex) {
        if (findDetalleCompras(detalleCompras.getIddetallecompra()) != null) {
            throw new Exception("DetalleCompras " + detalleCompras + " ya existe.", ex);
        }
        throw ex;
    } finally {
        if (em != null) {
            em.close();
        }
    }
}


    public void edit(DetalleCompras detalleCompras) throws Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            detalleCompras = em.merge(detalleCompras);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findDetalleCompras(detalleCompras.getIddetallecompra()) == null) {
                throw new Exception("El detalle de compra con id " + detalleCompras.getIddetallecompra() + " no existe.");
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
            em.getTransaction().begin();
            DetalleCompras detalleCompras;
            try {
                detalleCompras = em.getReference(DetalleCompras.class, id);
            } catch (EntityNotFoundException enfe) {
                throw new Exception("El detalle de compra con id " + id + " no existe.", enfe);
            }
            em.remove(detalleCompras);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<DetalleCompras> findDetalleComprasEntities() {
        return findDetalleComprasEntities(true, -1, -1);
    }

    public List<DetalleCompras> findDetalleComprasEntities(int maxResults, int firstResult) {
        return findDetalleComprasEntities(false, maxResults, firstResult);
    }

    private List<DetalleCompras> findDetalleComprasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(DetalleCompras.class));
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

    public DetalleCompras findDetalleCompras(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DetalleCompras.class, id);
        } finally {
            em.close();
        }
    }

    public int getDetalleComprasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<DetalleCompras> rt = cq.from(DetalleCompras.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    
}
