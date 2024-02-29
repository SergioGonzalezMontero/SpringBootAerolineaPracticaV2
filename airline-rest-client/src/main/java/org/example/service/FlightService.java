package org.example.service;


import org.example.api.ApiFlightService;
import org.example.dto.FlightDTO;
import org.example.exception.BadRequestException;
import org.example.exception.NotFoundException;
import org.example.exception.NotLoggedCorretlyException;
import org.example.exception.NotPermissException;
import org.example.tools.Tools;

import java.util.Scanner;

/**
 * Servicio para manejar operaciones relacionadas con vuelos.
 */
public class FlightService {
    private final ApiFlightService apiFlightService = new ApiFlightService();
    private final Tools tools = new Tools();

    /**
     * Busca vuelos por origen y destino.
     *
     * @param scanner Scanner para leer la entrada del usuario.
     */
    public void findflightOrigenDestiny(Scanner scanner) {
        System.out.println("Consulta vuelos por origen y destino");
        System.out.println("Introduce Origen:");
        String origen = scanner.nextLine().trim().toUpperCase();
        System.out.println("Introduce Destino:");
        String destino = scanner.nextLine().trim().toUpperCase();

        // Comprueba si los datos introducidos son válidos
        if (tools.esTextoValido(origen) && tools.esTextoValido(destino)) {
            try {
                // Llama al servicio para buscar vuelos por origen y destino
                FlightDTO[] flightDTO = apiFlightService.findFlightOrigenDestiny(origen, destino);
                for (FlightDTO flight : flightDTO) {
                    printFlight(flight);
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
            System.out.println("Los parametros introducidos no son válidos");
        }
    }

    /**
     * Busca vuelo por código y fecha.
     *
     * @param scanner Scanner para leer la entrada del usuario.
     * @return El objeto FlightDTO correspondiente al vuelo encontrado.
     */
    public FlightDTO findflightCodeDate(Scanner scanner) {
        System.out.println("Consulta vuelo por codigo y fecha");
        System.out.println("Introduce el código:");
        String code = scanner.nextLine().trim().toUpperCase();
        System.out.println("Introduce la fecha: (aaaa-mm-dd)");
        String date = scanner.nextLine().trim();

        // Comprueba si los datos introducidos son válidos
        if (tools.esTextoValido(code) && tools.esFechaValida(date)) {
            try {
                // Llama al servicio para buscar vuelo por código y fecha
                FlightDTO flightDTO = apiFlightService.findflightCodeDate(code, date);
                printFlight(flightDTO);
                return flightDTO;
            } catch (BadRequestException e) {
                System.out.println("La fecha no tiene un formato válido");
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
            System.out.println("Los parametros introducidos no son válidos");
        }
        return null;
    }

    /**
     * Crea un nuevo vuelo.
     *
     * @param scanner Scanner para leer la entrada del usuario.
     */
    public void createFligth(Scanner scanner) {
        System.out.println("Crear vuelo");
        System.out.println("Introduce codigo:");
        String code = scanner.nextLine().trim().toUpperCase();
        System.out.println("Introduce origen:");
        String origin = scanner.nextLine().trim().toUpperCase();
        System.out.println("Introduce destino:");
        String destination = scanner.nextLine().trim().toUpperCase();
        System.out.println("Introduce la fecha: (aaaa-mm-dd)");
        String date = scanner.nextLine().trim();

        // Comprueba si los datos introducidos son válidos
        if (tools.esTextoValido(code) && tools.esTextoValido(origin) && tools.esTextoValido(destination) && tools.esFechaValida(date)) {
            String id = code + "-" + date;
            FlightDTO flightDTO = new FlightDTO(code, origin, destination, date, id);
            System.out.println("La ID de vuelo es " + flightDTO.getId());
            try {
                // Llama al servicio para crear el vuelo
                apiFlightService.create(flightDTO);
                System.out.println("Vuelo creado");
                printFlight(flightDTO);
            } catch (BadRequestException e) {
                System.out.println("La fecha no tiene un formato válido");
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
            System.out.println("Los parametros introducidos no son válidos");
        }
    }

    /**
     * Elimina un vuelo.
     *
     * @param scanner Scanner para leer la entrada del usuario.
     */
    public void deleteFlight(Scanner scanner) {
        FlightDTO flightDTO;
        try {
            flightDTO = findflightCodeDate(scanner);
            if (tools.confirmarAccion(scanner)) {
                apiFlightService.delete(flightDTO.getId());
                System.out.println("Vuelo eliminado");
            } else {
                System.out.println("No se ha confirmado la eliminación");
            }

        } catch (NotPermissException e) {
            System.out.println("Usuario sin permisos");
        } catch (NotLoggedCorretlyException e) {
            System.out.println("Usuario no logueado");
        } catch (NotFoundException e) {
            System.out.println("Usuario no encontrado");
        } catch (Exception e) {
            System.out.println("No se ha realizado la eliminación");
        }
    }

    /**
     * Muestra todos los vuelos.
     */
    public void findAll() {
        try {
            FlightDTO[] flightDTO = apiFlightService.findAll();

            for (FlightDTO flight : flightDTO) {
                printFlight(flight);
            }

        } catch (BadRequestException e) {
            System.out.println("No hay vuelos disponibles");
        } catch (NotFoundException e) {
            System.out.println("Vuelos no encontrado");
        } catch (NotPermissException e) {
            System.out.println("Usuario sin permisos");
        } catch (NotLoggedCorretlyException e) {
            System.out.println("Usuario no logueado");
        } catch (RuntimeException e) {
            System.out.println("El server no está disponible");
        } catch (Exception e) {
            System.out.println("Error inesperado");
        }
    }

    /**
     * Imprime los detalles de un vuelo.
     *
     * @param flightDTO Objeto FlightDTO que representa el vuelo.
     */
    private void printFlight(FlightDTO flightDTO) {
        System.out.println("----------------------------");
        System.out.println("Detalles del vuelo:");
        System.out.println("Código: " + flightDTO.getCode());
        System.out.println("Origen: " + flightDTO.getOrigin());
        System.out.println("Destino: " + flightDTO.getDestination());
        System.out.println("Fecha: " + flightDTO.getDate());
        System.out.println("ID: " + flightDTO.getId());
        System.out.println("----------------------------");
    }
}
