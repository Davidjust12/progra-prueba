/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;

import CapaLogica_ventas.Cliente;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import java.util.List;

public class ClienteJpaController {

    private EntityManagerFactory emf;

    public ClienteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cliente cliente) {
    EntityManager em = getEntityManager();
    EntityTransaction tx = em.getTransaction();
    try {
        System.out.println("Iniciando transacción para guardar el cliente");
        tx.begin();
        em.persist(cliente);
        tx.commit();
        System.out.println("Cliente guardado con éxito");
    } catch (Exception e) {
        if (tx.isActive()) {
            tx.rollback();
        }
        System.out.println("Error al guardar el cliente: " + e.getMessage());
        e.printStackTrace();
    } finally {
        em.close();
    }


    }
    public Cliente findCliente(String id) {
    EntityManager em = getEntityManager();
    try {
        return em.find(Cliente.class, id);
    } finally {
        em.close();
    }
}


    public void edit(Cliente cliente) {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(cliente);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public void remove(Cliente cliente) {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.remove(em.merge(cliente));
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
public void delete(String id) {
    EntityManager em = getEntityManager();
    EntityTransaction tx = em.getTransaction();
    try {
        tx.begin();
        Cliente cliente = em.find(Cliente.class, id);
        if (cliente != null) {
            em.remove(cliente);
            tx.commit();
            System.out.println("Cliente eliminado con éxito.");
        } else {
            System.out.println("Cliente no encontrado.");
        }
    } catch (Exception e) {
        if (tx.isActive()) {
            tx.rollback();
        }
        System.out.println("Error al eliminar el cliente: " + e.getMessage());
        e.printStackTrace();
    } finally {
        em.close();
    }
}

    public Cliente find(Object id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cliente.class, id);
        } finally {
            em.close();
        }
    }

    public List<Cliente> findAll() {
        EntityManager em = getEntityManager();
        try {
            javax.persistence.criteria.CriteriaQuery<Cliente> cq = em.getCriteriaBuilder().createQuery(Cliente.class);
            cq.select(cq.from(Cliente.class));
            return em.createQuery(cq).getResultList();
        } finally {
            em.close();
        }
    }

    public List<Cliente> findRange(int[] range) {
        EntityManager em = getEntityManager();
        try {
            javax.persistence.criteria.CriteriaQuery<Cliente> cq = em.getCriteriaBuilder().createQuery(Cliente.class);
            cq.select(cq.from(Cliente.class));
            javax.persistence.Query q = em.createQuery(cq);
            q.setMaxResults(range[1] - range[0]);
            q.setFirstResult(range[0]);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public int count() {
        EntityManager em = getEntityManager();
        try {
            javax.persistence.criteria.CriteriaQuery<Long> cq = em.getCriteriaBuilder().createQuery(Long.class);
            javax.persistence.criteria.Root<Cliente> rt = cq.from(Cliente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            return em.createQuery(cq).getSingleResult().intValue();
        } finally {
            em.close();
        }
    }
}
