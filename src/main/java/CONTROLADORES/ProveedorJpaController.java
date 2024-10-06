/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CONTROLADORES;

import CapaLogica_ventas.Proveedor;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class ProveedorJpaController {

    private EntityManagerFactory emf = null;

    public ProveedorJpaController() {
        this.emf = Persistence.createEntityManagerFactory("CapaLogica_ventas_jar_1.0-SNAPSHOTPU");
    }

    public ProveedorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Proveedor proveedor) throws Exception {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(proveedor);
            tx.commit();
        } catch (Exception ex) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw new Exception("Error al guardar el proveedor", ex);
        } finally {
            em.close();
        }
    }
     public void edit(Proveedor proveedor) throws Exception {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(proveedor);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }


    public Proveedor buscarProveedor(String idProveedor) {
    EntityManager em = getEntityManager();
    try {
        return em.find(Proveedor.class, idProveedor);
    } finally {
        em.close();
    }
}

public Proveedor findProveedor(String id) {
    EntityManager em = getEntityManager();
    try {
        return em.find(Proveedor.class, id); // Busca por ID en la entidad Proveedor
    } finally {
        em.close();
    }
}

    public void destroy(String id) throws Exception {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Proveedor proveedor;
            try {
                proveedor = em.getReference(Proveedor.class, id);
                proveedor.getIdproveedor();
            } catch (Exception enfe) {
                throw new Exception("El proveedor con id " + id + " no existe.", enfe);
            }
            em.remove(proveedor);
            tx.commit();
        } catch (Exception ex) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw new Exception("Error al eliminar el proveedor", ex);
        } finally {
            em.close();
        }
    }
}
