/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

import CapaLogica_ventas.Cliente;
import controladores.ClienteJpaController;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final String PERSISTENCE_UNIT_NAME = "CapaLogica_ventas_jar_1.0-SNAPSHOTPU"; 
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    private static ClienteJpaController clienteJpaController = new ClienteJpaController(emf);
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int option;
                
        do {
            printMenu();
            option = readOption();
            executeOption(option);
        } while (option != 5);
    }

    private static void printMenu() {
        System.out.println("----- Menú de Clientes -----");
        System.out.println("1. Crear Cliente");
        System.out.println("2. Leer Cliente");
        System.out.println("3. Actualizar Cliente");
        System.out.println("4. Eliminar Cliente");
        System.out.println("5. Salir");
        System.out.print("Seleccione una opción: ");
    }

    private static int readOption() {
        int option = -1;
        try {
            option = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Opción inválida. Por favor ingrese un número.");
            scanner.next(); // Limpiar el buffer
        }
        return option;
    }

    private static void executeOption(int option) {
        switch (option) {
            case 1:
                createCliente();
                break;
            case 2:
                readCliente();
                break;
            case 3:
                updateCliente();
                break;
            case 4:
                deleteCliente();
                break;
            case 5:
                System.out.println("Saliendo...");
                break;
            default:
                System.out.println("Opción no válida. Por favor seleccione una opción del menú.");
        }
    }

    private static void createCliente() {
        System.out.println("Ingrese los datos del cliente:");
        System.out.print("ID Cliente: ");
        String id = scanner.next();
        System.out.print("Nombre: ");
        String nombre = scanner.next();
        System.out.print("Apellido: ");
        String apellido = scanner.next();
        System.out.print("NIT (número entero): ");
        Integer nit = scanner.nextInt();
        System.out.print("Teléfono: ");
        String telefono = scanner.next();
        System.out.print("Dirección: ");
        String direccion = scanner.next();

        Cliente cliente = new Cliente(id);
        cliente.setNombre(nombre);
        cliente.setApellido(apellido);
        cliente.setNit(nit);
        cliente.setTelefono(telefono);
        cliente.setDireccion(direccion);

        clienteJpaController.create(cliente);
        System.out.println("Cliente creado con éxito.");
    }

    private static void readCliente() {
        System.out.print("Ingrese el ID del cliente que desea buscar: ");
        String id = scanner.next();
        Cliente cliente = clienteJpaController.find(id);

        if (cliente != null) {
            System.out.println("Cliente encontrado: " + cliente);
        } else {
            System.out.println("Cliente no encontrado.");
        }
    }

    private static void updateCliente() {
        System.out.print("Ingrese el ID del cliente que desea actualizar: ");
        String id = scanner.next();
        Cliente cliente = clienteJpaController.find(id);

        if (cliente != null) {
            System.out.println("Ingrese los nuevos datos del cliente:");
            System.out.print("Nombre (" + cliente.getNombre() + "): ");
            cliente.setNombre(scanner.next());
            System.out.print("Apellido (" + cliente.getApellido() + "): ");
            cliente.setApellido(scanner.next());
            System.out.print("NIT (" + cliente.getNit() + "): ");
            cliente.setNit(scanner.nextInt());
            System.out.print("Teléfono (" + cliente.getTelefono() + "): ");
            cliente.setTelefono(scanner.next());
            System.out.print("Dirección (" + cliente.getDireccion() + "): ");
            cliente.setDireccion(scanner.next());

            clienteJpaController.edit(cliente);
            System.out.println("Cliente actualizado con éxito.");
        } else {
            System.out.println("Cliente no encontrado.");
        }
    }

    private static void deleteCliente() {
        System.out.print("Ingrese el ID del cliente que desea eliminar: ");
        String id = scanner.next();
        Cliente cliente = clienteJpaController.find(id);

        if (cliente != null) {
            clienteJpaController.remove(cliente);
            System.out.println("Cliente eliminado con éxito.");
        } else {
            System.out.println("Cliente no encontrado.");
        }
    }
}
