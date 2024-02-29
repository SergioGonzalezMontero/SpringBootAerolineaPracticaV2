package org.example.core;

import org.example.dto.UserDTO;
import org.example.service.FlightService;
import org.example.service.PassengerService;
import org.example.service.UserService;

import java.util.Scanner;

/**
 * Esta clase representa un cliente para interactuar con la aplicación.
 * Proporciona un menú interactivo para acceder a diferentes funcionalidades.
 */
public class Client {
    public static UserDTO userLogged;

    // Se crean instancias de los servicios de vuelo pasajero y usuario
    FlightService flightService = new FlightService();
    PassengerService passengerService = new PassengerService();

    UserService userService = new UserService();

    /**
     * Método principal para ejecutar el cliente.
     *
     * @throws Exception si ocurre un error durante la ejecución.
     */
    public void run() throws Exception {
        String opt = "";
        try (Scanner scanner = new Scanner(System.in)) {
            // Se muestra el menú principal y se espera la entrada del usuario
            while (!"0".equals(opt)) {
                printMenu();
                opt = scanner.nextLine();
                // Se maneja la opción seleccionada por el usuario
                switch (opt) {
                    case "1":
                        // Se muestra el menú de vuelos
                        menuFlight(scanner);
                        break;
                    case "2":
                        // Se muestra el menú de pasajeros
                        menuPassenger(scanner);
                        break;
                    case "3":
                        // Se muestra el menú de usuarios
                        menuUser(scanner);
                        break;
                    case "0":
                        System.out.println("Gracias por usar la aplicación");
                        break;
                    default:
                        System.out.println("Opción no válida");
                }
            }
        }
    }

    /**
     * Método para mostrar y manejar el menú de vuelos.
     *
     * @param scanner El objeto Scanner para la entrada del usuario.
     */
    private void menuFlight(Scanner scanner) {
        String opt;
        do {
            printMenuFlight();
            opt = scanner.nextLine();
            // Se manejan las opciones del menú de vuelos
            switch (opt) {
                case "1":
                    flightService.createFligth(scanner);
                    break;
                case "2":
                    flightService.findflightOrigenDestiny(scanner);
                    break;
                case "3":
                    flightService.findflightCodeDate(scanner);
                    break;
                case "4":
                    flightService.deleteFlight(scanner);
                    break;
                case "0":
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción no válida");
            }
        } while (!"0".equals(opt));
    }

    /**
     * Método para mostrar y manejar el menú de pasajeros.
     *
     * @param scanner El objeto Scanner para la entrada del usuario.
     * @throws Exception si ocurre un error durante la ejecución.
     */
    private void menuPassenger(Scanner scanner) throws Exception {
        String opt;
        do {
            printMenuPassenger();
            opt = scanner.nextLine();
            // Se manejan las opciones del menú de pasajeros
            switch (opt) {
                case "1":
                    passengerService.newPassenger(scanner);
                    break;
                case "2":
                    passengerService.checkPassengerOfFlignt(scanner);
                    break;
                case "3":
                    passengerService.updatePassangerInFlight(scanner);
                    break;
                case "4":
                    passengerService.deletePassenger(scanner);
                    break;
                case "5":
                    passengerService.checkAllPassengerOfFlight(scanner);
                    break;
                case "0":
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción no válida");
            }
        } while (!"0".equals(opt));
    }

    private void menuUser(Scanner scanner) throws Exception {
        String opt;
        do {
            printMenuUser();
            opt = scanner.nextLine();
            // Se manejan las opciones del menú de pasajeros
            switch (opt) {
                case "1":
                    userService.newUser(scanner);
                    break;
                case "2":
                    userService.deleteUser(scanner);
                    break;
                case "3":
                    userService.updateUser(scanner);
                    break;
                case "4":
                    userService.findUser(scanner);
                    break;
                case "5":
                    userService.login(scanner);
                case "0":
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción no válida");
            }
        } while (!"0".equals(opt));
    }

    /**
     * Método para imprimir el menú principal.
     */
    private void printMenu() {
        System.out.println("==== Bienvenido a la aplicación de gestión de vuelos ====");
        System.out.println("Seleccione una opción:");
        System.out.println("1. Menú de vuelos");
        System.out.println("2. Menú de pasajeros");
        System.out.println("3. Menú de usuarios");
        System.out.println("0. Salir");
    }

    /**
     * Método para imprimir el menú de vuelos.
     */
    private void printMenuFlight() {
        System.out.println("==== Menú de vuelos ====");
        System.out.println("Seleccione una opción:");
        System.out.println("1. Nuevo vuelo");
        System.out.println("2. Consultar vuelos por origen y destino");
        System.out.println("3. Consultar vuelo por código y fecha");
        System.out.println("4. Eliminar vuelo por código");
        System.out.println("5. Mostrar todos los vuelos");
        System.out.println("0. Volver al menú principal");
    }

    /**
     * Método para imprimir el menú de pasajeros.
     */
    private void printMenuPassenger() {
        System.out.println("==== Menú de pasajeros ====");
        System.out.println("Seleccione una opción:");
        System.out.println("1. Registrar nuevo pasajero en un vuelo");
        System.out.println("2. Consultar si un pasajero está en un vuelo");
        System.out.println("3. Actualizar información de un pasajero en un vuelo");
        System.out.println("4. Eliminar pasajero de un vuelo");
        System.out.println("5. Consultar todos los pasajeros de un vuelo");
        System.out.println("0. Volver al menú principal");
    }

    /**
     * Método para imprimir el menú de pasajeros.
     */
    private void printMenuUser() {
        System.out.println("==== Menú de usuarios ====");
        System.out.println("Seleccione una opción:");
        System.out.println("1. Creacción usuario");
        System.out.println("2. Eliminación de usuario");
        System.out.println("3. Modificación de usuario");
        System.out.println("4. Consulta de usuario");
        System.out.println("5. Iniciar sesion");
        System.out.println("0. Volver al menú principal");
    }
}