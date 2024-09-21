/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;

import CapaLogica_ventas.Empleados;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class EmpleadosJpaController implements Serializable {

    private EntityManagerFactory emf = null;

    public EmpleadosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Empleados empleado) throws Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            EntityTransaction et = em.getTransaction();
            et.begin();
            em.persist(empleado);
            et.commit();
        } catch (Exception ex) {
            if (findEmpleados(empleado.getIdempleado()) != null) {
                throw new Exception("Empleado " + empleado + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Empleados empleado) throws Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            EntityTransaction et = em.getTransaction();
            et.begin();
            empleado = em.merge(empleado);
            et.commit();
        } catch (Exception ex) {
            String id = empleado.getIdempleado();
            if (findEmpleados(id) == null) {
                throw new Exception("The empleado with id " + id + " no longer exists.", ex);
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
            Empleados empleado;
            try {
                empleado = em.getReference(Empleados.class, id);
                empleado.getIdempleado(); // Trigger the exception if the id does not exist
            } catch (Exception enfe) {
                throw new Exception("The empleado with id " + id + " no longer exists.", enfe);
            }
            em.remove(empleado);
            et.commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public Empleados findEmpleados(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Empleados.class, id);
        } finally {
            em.close();
        }
    }

    public List<Empleados> findEmpleadosEntities() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Empleados> q = em.createNamedQuery("Empleados.findAll", Empleados.class);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public int getEmpleadosCount() {
        EntityManager em = getEntityManager();
        try {
            javax.persistence.criteria.CriteriaQuery<Long> cq = em.getCriteriaBuilder().createQuery(Long.class);
            javax.persistence.criteria.Root<Empleados> rt = cq.from(Empleados.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            javax.persistence.Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
}
