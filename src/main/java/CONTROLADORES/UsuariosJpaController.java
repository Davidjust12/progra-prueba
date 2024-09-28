/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;

import CONTROLADORES.exceptions.NonexistentEntityException;
import CONTROLADORES.exceptions.PreexistingEntityException;
import CapaLogica_ventas.Empleados;
import CapaLogica_ventas.Usuarios;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

public class UsuariosJpaController implements Serializable {

    public UsuariosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    // Crear un usuario
    public void create(Usuarios usuario) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();

            // Verificar si el empleado asociado existe
            Empleados empleado = usuario.getIdempleado();
            if (empleado != null) {
                empleado = em.getReference(Empleados.class, empleado.getIdempleado());
                usuario.setIdempleado(empleado);
            }

            em.persist(usuario);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findUsuario(usuario.getIdusuario()) != null) {
                throw new PreexistingEntityException("Usuario " + usuario + " ya existe.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    public List<Usuarios> findUsuariosByEmpleadoId(String idEmpleado) {
    EntityManager em = getEntityManager();
    try {
        // Consulta para buscar usuarios por el idEmpleado
        Query query = em.createQuery("SELECT u FROM Usuarios u WHERE u.idempleado.idempleado = :idEmpleado");
        query.setParameter("idEmpleado", idEmpleado);
        return query.getResultList();
    } finally {
        em.close();
    }
}

    public void destroyByEmpleadoId(String idEmpleado) throws NonexistentEntityException, Exception {
    EntityManager em = getEntityManager();
    try {
        // Iniciar la transacción
        em.getTransaction().begin();
        
        // Buscar el usuario por el idEmpleado
        Query query = em.createQuery("SELECT u FROM Usuarios u WHERE u.idempleado.idempleado = :idEmpleado");
        query.setParameter("idEmpleado", idEmpleado);
        Usuarios usuario = (Usuarios) query.getSingleResult(); // Obtener el usuario
        
        if (usuario != null) {
            // Eliminar el usuario
            em.remove(em.merge(usuario));
        } else {
            throw new NonexistentEntityException("No se encontró el usuario para el empleado con ID " + idEmpleado);
        }
        
        // Confirmar la transacción
        em.getTransaction().commit();
    } catch (Exception ex) {
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
        throw ex;
    } finally {
        em.close();
    }
}

    public Usuarios findUsuarioPorNombre(String nombreUsuario) {
    EntityManager em = getEntityManager();
    try {
        return (Usuarios) em.createQuery("SELECT u FROM Usuarios u WHERE u.nombre = :nombre")
                .setParameter("nombre", nombreUsuario)
                .getSingleResult();
    } catch (NoResultException e) {
        return null; // Usuario no encontrado
    } finally {
        em.close();
    }
}

    public Usuarios findUsuarioPorUsuario(String nombreUsuario) {
    EntityManager em = getEntityManager();
    try {
        return (Usuarios) em.createNamedQuery("Usuarios.findByUsuario") // Utiliza el NamedQuery
                .setParameter("usuario", nombreUsuario)
                .getSingleResult();
    } catch (NoResultException e) {
        return null; // Usuario no encontrado
    } finally {
        em.close();
    }
}
public Usuarios findUserByTrabajadorId(String idTrabajador) {
    EntityManager em = getEntityManager();
    try {
        // Suponiendo que la entidad Usuarios tiene una relación con Empleados y usa el idTrabajador
        TypedQuery<Usuarios> query = em.createQuery("SELECT u FROM Usuarios u WHERE u.idempleado.idtrabajador = :idTrabajador", Usuarios.class);
        query.setParameter("idTrabajador", idTrabajador);

        List<Usuarios> resultados = query.getResultList();

        if (!resultados.isEmpty()) {
            return resultados.get(0); // Devolver el primer usuario encontrado
        } else {
            return null;
        }
    } finally {
        em.close();
    }
}


    // Editar un usuario existente
    public void edit(Usuarios usuario) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();

            Usuarios usuarioPersistent = em.find(Usuarios.class, usuario.getIdusuario());
            Empleados empleadoOld = usuarioPersistent.getIdempleado();
            Empleados empleadoNew = usuario.getIdempleado();

            if (empleadoNew != null) {
                empleadoNew = em.getReference(Empleados.class, empleadoNew.getIdempleado());
                usuario.setIdempleado(empleadoNew);
            }

            usuario = em.merge(usuario);
            em.getTransaction().commit();
        } catch (Exception ex) {
            Integer id = usuario.getIdusuario();
            if (findUsuario(id) == null) {
                throw new NonexistentEntityException("El usuario con id " + id + " no existe.");
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    // Eliminar un usuario
    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuarios usuario;
            try {
                usuario = em.getReference(Usuarios.class, id);
                usuario.getIdusuario();
            } catch (Exception ex) {
                throw new NonexistentEntityException("El usuario con id " + id + " no existe.", ex);
            }
            em.remove(usuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    // Buscar un usuario por ID
    public Usuarios findUsuario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuarios.class, id);
        } finally {
            em.close();
        }
    }

    // Listar todos los usuarios
    public List<Usuarios> findUsuariosEntities() {
        return findUsuariosEntities(true, -1, -1);
    }

    public List<Usuarios> findUsuariosEntities(int maxResults, int firstResult) {
        return findUsuariosEntities(false, maxResults, firstResult);
    }

    private List<Usuarios> findUsuariosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuarios.class));
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

    public int getUsuariosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuarios> rt = cq.from(Usuarios.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
} ......