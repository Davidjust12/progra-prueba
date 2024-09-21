/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;

import CapaLogica_ventas.Categoria;
import CapaLogica_ventas.Producto;
import java.io.Serializable;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;
import javax.persistence.Query;

public class ProductoJpaController implements Serializable {

    private EntityManagerFactory emf = null;

    public ProductoJpaController(EntityManagerFactory emf) {
        this.emf = Persistence.createEntityManagerFactory("CapaLogica_ventas_jar_1.0-SNAPSHOTPU");
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Producto producto) throws Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            EntityTransaction et = em.getTransaction();
            et.begin();
            em.persist(producto);
            et.commit();
        } catch (Exception ex) {
            if (findProducto(producto.getIdproducto()) != null) {
                throw new Exception("Producto " + producto + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Producto producto) throws Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            EntityTransaction et = em.getTransaction();
            et.begin();
            producto = em.merge(producto);
            et.commit();
        } catch (Exception ex) {
            String id = producto.getIdproducto();
            if (findProducto(id) == null) {
                throw new Exception("The producto with id " + id + " no longer exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            EntityTransaction et = em.getTransaction();
            et.begin();
            Producto producto;
            try {
                producto = em.getReference(Producto.class, id);
                producto.getIdproducto(); // Trigger the exception if the id does not exist
            } catch (Exception enfe) {
                throw new Exception("The producto with id " + id + " no longer exists.", enfe);
            }
            em.remove(producto);
            et.commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
public List<Categoria> findAll() {
    EntityManager em = getEntityManager();
    try {
        return em.createQuery("SELECT c FROM Categoria c", Categoria.class).getResultList();
    } finally {
        em.close();
    }
}

    public List<Producto> findProductoEntities() {
        return findProductoEntities(true, -1, -1);
    }

    public List<Producto> findProductoEntities(int maxResults, int firstResult) {
        return findProductoEntities(false, maxResults, firstResult);
    }

    private List<Producto> findProductoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Producto> q = em.createNamedQuery("Producto.findAll", Producto.class);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }
public int getSiguienteIdProducto() {
    EntityManager em = getEntityManager();
    try {
        Query query = em.createQuery("SELECT MAX(p.idproducto) FROM Producto p");
        Integer maxId = (Integer) query.getSingleResult();
        return (maxId == null) ? 1 : maxId + 1; // Si no hay productos, devuelve 1
    } finally {
        em.close();
    }
}
    public Producto findProducto(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Producto.class, id);
        } finally {
            em.close();
        }
    }

    public int getProductoCount() {
        EntityManager em = getEntityManager();
        try {
            javax.persistence.criteria.CriteriaQuery<Long> cq = em.getCriteriaBuilder().createQuery(Long.class);
            javax.persistence.criteria.Root<Producto> rt = cq.from(Producto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            javax.persistence.Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
}

