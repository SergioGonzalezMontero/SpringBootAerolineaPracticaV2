package org.example.service;

import org.example.api.ApiPassengerService;
import org.example.dto.PassengerDTO;
import org.example.exception.BadRequestException;
import org.example.exception.NotFoundException;
import org.example.exception.NotLoggedCorretlyException;
import org.example.exception.NotPermissException;
import org.example.tools.Tools;

import java.util.Scanner;

public class PassengerService {

    private final ApiPassengerService apiPassengerService = new ApiPassengerService();
    private final Tools tools = new Tools();

    public void newPassenger(Scanner scanner) {
        System.out.println("Asocia un pasajero a un vuelo");
        System.out.println("Introduce el id del vuelo:");
        String id = scanner.nextLine().trim().toUpperCase();
        System.out.println("Introduce el NIE del pasajero:");
        String nif = scanner.nextLine().trim().toUpperCase();
        System.out.println("Introduce el nombre del pasajero:");
        String name = scanner.nextLine().trim();
        System.out.println("Introduce el apellido del pasajero:");
        String surname = scanner.nextLine().trim();
        System.out.println("Introduce el email del pasajero:");
        String email = scanner.nextLine().trim();
        System.out.println("Introduce numero de asiento:");
        String seatNumber = scanner.nextLine().trim();

        // Comprueba si los datos introducidos son válidos
        if (tools.esTextoValido(id) && tools.esDNIValido(nif) && tools.esTextoValido(name) && tools.esTextoValido(surname) && tools.esTextoValido(email) && tools.esNumeroValido(seatNumber)) {

            int seat = Integer.parseInt(seatNumber);
            PassengerDTO passengerDTO = new PassengerDTO(nif, id, name, surname, email, seat);
            try {
                // Llama al servicio para crear el pasajero
                apiPassengerService.create(passengerDTO);
                System.out.println("Pasajero creado correctamente");
            } catch (BadRequestException e) {
                System.out.println("Los parametros introducidos no son correctos");
            } catch (NotFoundException e) {
                System.out.println("Vuelo no encontrado");
            } catch (NotPermissException e) {
                System.out.println("Usuario sin permisos");
            } catch (NotLoggedCorretlyException e) {
                System.out.println("Usuario no logueado");
            } catch (RuntimeException e) {
                System.out.println("El server no está disponible");
            } catch (Exception e) {
                System.out.println("Error inesperado");
            }
        } else {
            System.out.println("Los parametros introducidos no son válidos");
        }
    }

    /**
     * Obtiene y muestra todos los pasajeros de un vuelo dado su ID.
     *
     * @param scanner Scanner para leer la entrada del usuario.
     */
    public void checkAllPassengerOfFlight(Scanner scanner) {
        System.out.println("Saber todos los pasajeros de un vuelo");
        System.out.println("Introduce el id del vuelo:");
        String id = scanner.nextLine().trim().toUpperCase();

        // Comprueba si el ID del vuelo es válido
        if (tools.esTextoValido(id)) {
            try {
                // Llama al servicio para buscar los pasajeros del vuelo
                PassengerDTO[] passengerDTOs = apiPassengerService.findPassengersOfFlight(id);
                for (PassengerDTO passenger : passengerDTOs) {
                    printPassenger(passenger);
                }

            } catch (BadRequestException e) {
                System.out.println("Los parametros introducidos no son correctos");
            } catch (NotFoundException e) {
                System.out.println("Vuelo no encontrado");
            } catch (NotPermissException e) {
                System.out.println("Usuario sin permisos");
            } catch (NotLoggedCorretlyException e) {
                System.out.println("Usuario no logueado");
            } catch (RuntimeException e) {
                System.out.println("El server no está disponible");
            } catch (Exception e) {
                System.out.println("Error inesperado");
            }
        } else {
            System.out.println("El ID del vuelo no es válido");
        }
    }

    /**
     * Comprueba si un pasajero está registrado en un vuelo.
     *
     * @param scanner Scanner para leer la entrada del usuario.
     * @return El objeto PassengerDTO correspondiente al pasajero encontrado.
     */
    public PassengerDTO checkPassengerOfFlignt(Scanner scanner) {
        System.out.println("Introduce el id del vuelo:");
        String id = scanner.nextLine().trim().toUpperCase();
        System.out.println("Introduce el NIF del pasajero:");
        String nif = scanner.nextLine().trim().toUpperCase();

        // Comprueba si los datos introducidos son válidos
        if (tools.esTextoValido(id) && tools.esTextoValido(nif)) {
            try {
                // Llama al servicio para buscar el pasajero en el vuelo
                PassengerDTO passengerDTO = apiPassengerService.findPassengerOfFlight(id, nif);
                printPassenger(passengerDTO);
                return passengerDTO;
            } catch (BadRequestException e) {
                System.out.println("Los parametros introducidos no son correctos");
            } catch (NotFoundException e) {
                System.out.println("Vuelo no encontrado");
            } catch (NotPermissException e) {
                System.out.println("Usuario sin permisos");
            } catch (NotLoggedCorretlyException e) {
                System.out.println("Usuario no logueado");
            } catch (RuntimeException e) {
                System.out.println("El server no está disponible");
            } catch (Exception e) {
                System.out.println("Error inesperado");
            }
        } else {
            System.out.println("Los parametros introducidos no son válidos");
        }
        return null;
    }

    /**
     * Actualiza la información de un pasajero en un vuelo.
     *
     * @param scanner Scanner para leer la entrada del usuario.
     */
    public void updatePassangerInFlight(Scanner scanner) {
        System.out.println("Actualiza un pasajero en un vuelo");

        PassengerDTO passengerDTO = checkPassengerOfFlignt(scanner);
        if (passengerDTO != null) {
            String id = passengerDTO.getFlightId();
            String nif = passengerDTO.getNif();

            // Menú de opciones
            System.out.println("¿Qué deseas modificar?");
            System.out.println("1. Nombre");
            System.out.println("2. Apellido");
            System.out.println("3. Email");
            System.out.println("4. Número de asiento");
            System.out.println("5. Todo");
            System.out.println("6. Volver sin modificar");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir la nueva línea después de nextInt()

            switch (opcion) {
                case 1:
                    System.out.println("Introduce el nuevo nombre:");
                    String newName = scanner.nextLine().trim();
                    passengerDTO.setName(newName);
                    break;
                case 2:
                    System.out.println("Introduce el nuevo apellido:");
                    String newSurname = scanner.nextLine().trim();
                    passengerDTO.setSurname(newSurname);
                    break;
                case 3:
                    System.out.println("Introduce el nuevo email:");
                    String newEmail = scanner.nextLine().trim();
                    passengerDTO.setEmail(newEmail);
                    break;
                case 4:
                    System.out.println("Introduce el nuevo número de asiento:");
                    String newSeatNumberStr = scanner.nextLine().trim();
                    if (tools.esNumeroValido(newSeatNumberStr)) {
                        int newSeatNumber = Integer.parseInt(newSeatNumberStr);
                        passengerDTO.setSeatNumber(newSeatNumber);
                    } else {
                        System.out.println("Número de asiento no válido");
                    }
                    break;
                case 5:
                    System.out.println("Introduce el nuevo nombre:");
                    String nuevoNombre = scanner.nextLine().trim();
                    passengerDTO.setName(nuevoNombre);

                    System.out.println("Introduce el nuevo apellido:");
                    String nuevoApellido = scanner.nextLine().trim();
                    passengerDTO.setSurname(nuevoApellido);

                    System.out.println("Introduce el nuevo email:");
                    String nuevoEmail = scanner.nextLine().trim();
                    passengerDTO.setEmail(nuevoEmail);

                    System.out.println("Introduce el nuevo número de asiento:");
                    String nuevoNumeroAsientoStr = scanner.nextLine().trim();
                    if (tools.esNumeroValido(nuevoNumeroAsientoStr)) {
                        int nuevoNumeroAsiento = Integer.parseInt(nuevoNumeroAsientoStr);
                        passengerDTO.setSeatNumber(nuevoNumeroAsiento);
                    } else {
                        System.out.println("Número de asiento no válido");
                    }
                    break;
                case 6:
                    System.out.println("Volviendo sin modificar...");
                    return; // Salir del método sin realizar ninguna modificación
                default:
                    System.out.println("Opción no válida");
            }
            try {
                if (tools.confirmarAccion(scanner)) {
                    // Llama al servicio para actualizar el pasajero en el vuelo
                    apiPassengerService.updatePassengerOfFlight(passengerDTO, id, nif);
                    System.out.println("Pasajero actualizado");
                    printPassenger(passengerDTO);
                } else {
                    System.out.println("No se confirmó la actualización");
                }
            } catch (NotPermissException e) {
                System.out.println("Usuario sin permisos");
            } catch (NotLoggedCorretlyException e) {
                System.out.println("Usuario no logueado");
            } catch (NotFoundException e) {
                System.out.println("Usuario no encontrado");
            } catch (RuntimeException e) {
                System.out.println("El server no está disponible");
            } catch (Exception e) {
                System.out.println("Error inesperado");
            }
        } else {
            System.out.println("No se ha encontrado el pasajero en el vuelo");
        }
    }

    /**
     * Elimina un pasajero de un vuelo.
     *
     * @param scanner Scanner para leer la entrada del usuario.
     * @return true si la eliminación fue exitosa, false de lo contrario.
     */
    public boolean deletePassenger(Scanner scanner) {
        PassengerDTO passengerDTO = checkPassengerOfFlignt(scanner);
        if (passengerDTO != null) {
            try {
                if (tools.confirmarAccion(scanner)) {
                    // Llama al servicio para eliminar el pasajero del vuelo
                    apiPassengerService.deletePassangerOfFlight(passengerDTO, passengerDTO.getFlightId(), passengerDTO.getNif());
                    System.out.println("Pasajero eliminado correctamente");
                    return true;
                } else {
                    System.out.println("No se confirmó la eliminación");
                }
            } catch (NotPermissException e) {
                System.out.println("Usuario sin permisos");
            } catch (NotLoggedCorretlyException e) {
                System.out.println("Usuario no logueado");
            } catch (NotFoundException e) {
                System.out.println("Usuario no encontrado");
            } catch (Exception e) {
                System.out.println("Algo ha fallado, no se ha realizado la eliminación");
            }
        }
        return false;
    }

    private void printPassenger(PassengerDTO passenger) {
        System.out.println("--------------------------");
        System.out.println("NIF: " + passenger.getNif());
        System.out.println("ID del vuelo: " + passenger.getFlightId());
        System.out.println("Nombre: " + passenger.getName());
        System.out.println("Apellido: " + passenger.getSurname());
        System.out.println("Correo electrónico: " + passenger.getEmail());
        System.out.println("Número de asiento: " + passenger.getSeatNumber());
        System.out.println("--------------------------");
    }
}


