package org.educa.airline.controllers;

import jakarta.validation.Valid;
import org.educa.airline.dto.PassengerDTO;
import org.educa.airline.entity.Passenger;
import org.educa.airline.exceptions.NotExistFlight;
import org.educa.airline.exceptions.NotExistPassenger;
import org.educa.airline.mappers.PassengerMapper;
import org.educa.airline.services.PassengerService;
import org.educa.airline.tools.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador para gestionar los pasajeros en vuelos.
 */
@RestController
@RequestMapping(path = "/flights/{id}")
public class PassengerController {
    private final Tools tool;
    private final PassengerService passengerService;
    private final PassengerMapper passengerMapper;

    /**
     * Constructor del controlador.
     *
     * @param tool             Herramientas de utilidad.
     * @param passengerService Servicio para la gestión de pasajeros.
     * @param passengerMapper  Mapeador de pasajeros.
     */
    @Autowired
    public PassengerController(Tools tool, PassengerService passengerService,
                               PassengerMapper passengerMapper) {
        this.tool = tool;
        this.passengerService = passengerService;
        this.passengerMapper = passengerMapper;
    }

    /**
     * Crea un nuevo pasajero en un vuelo.
     *
     * @param id           ID del vuelo.
     * @param passengerDTO Datos del pasajero a crear.
     * @return ResponseEntity con el resultado de la operación.
     */
    @PostMapping(path = "/passenger")
    public ResponseEntity<Void> create(@PathVariable("id") String id, @RequestBody @Valid PassengerDTO passengerDTO) {
        Passenger passenger = passengerMapper.toEntity(passengerDTO);
        // Verifica si el DNI del pasajero es válido
        if (tool.esDNIValido(passenger.getNif())) {
            try {
                // Intenta crear el pasajero en el vuelo dado
                if (passengerService.create(id, passenger)) {
                    return ResponseEntity.status(201).build(); // Si se crea correctamente, devuelve 201 Created
                } else {
                    return ResponseEntity.status(409).build(); // Si ya existe un pasajero con ese DNI en el vuelo, devuelve 409 Conflict
                }
            } catch (NotExistFlight e) {
                return ResponseEntity.notFound().build(); // Si el vuelo no existe, devuelve 404 Not Found
            }
        } else {
            return ResponseEntity.badRequest().build(); // Si el DNI no es válido, devuelve 400 Bad Request
        }
    }

    /**
     * Obtiene todos los pasajeros de un vuelo.
     *
     * @param id ID del vuelo.
     * @return ResponseEntity con la lista de pasajeros.
     */
    @GetMapping(path = "/passengers")
    public ResponseEntity<List<PassengerDTO>> findAllPassengerOfFlight(@PathVariable("id") String id) {
        try {
            List<PassengerDTO> passengerDTOSs = passengerMapper.toDTOs(
                    passengerService.findAllPassangerOfFlight(id));
            return ResponseEntity.ok(passengerDTOSs); // Si se encuentran pasajeros, devuelve la lista de PassengerDTO
        } catch (NotExistFlight e) {
            return ResponseEntity.notFound().build(); // Si el vuelo no existe, devuelve 404 Not Found
        }

    }

    /**
     * Verifica si un pasajero existe en un vuelo.
     *
     * @param id  ID del vuelo.
     * @param nif DNI del pasajero.
     * @return ResponseEntity con los detalles del pasajero si existe.
     */
    @GetMapping(path = "/passenger/{nif}")
    public ResponseEntity<PassengerDTO> existPassenger(@PathVariable("id") String id, @PathVariable("nif") String nif) {

        try {
            // Intenta encontrar el pasajero en el vuelo dado
            return ResponseEntity.ok(passengerMapper.toDTO(passengerService.findOnePassanger(id, nif)));

        } catch (NotExistFlight | NotExistPassenger e) {

            return ResponseEntity.badRequest().build(); // Si el vuelo o el pasajero no existen, devuelve 400 Bad Request
        }

    }

    /**
     * Actualiza los detalles de un pasajero en un vuelo.
     *
     * @param id           ID del vuelo.
     * @param nif          DNI del pasajero.
     * @param passengerDTO Nuevos detalles del pasajero.
     * @return ResponseEntity con los detalles del pasajero actualizados.
     */
    @PutMapping(path = "/passenger/{nif}")
    public ResponseEntity<PassengerDTO> updatePassenger(@PathVariable("id") String id, @PathVariable("nif") String nif, @RequestBody PassengerDTO passengerDTO) {

        Passenger passenger = passengerMapper.toEntity(passengerDTO);
        // Verifica si el DNI del pasajero es válido
        if (tool.esDNIValido(nif)) {
            try {
                // Intenta actualizar los detalles del pasajero en el vuelo dado
                if (passengerService.update(passenger, id, nif)) {
                    // Si se actualiza correctamente, devuelve los detalles actualizados del pasajero
                    return ResponseEntity.ok(passengerMapper.toDTO(passengerService.findOnePassanger(passenger.getFlightId(), passenger.getNif())));
                } else {
                    return ResponseEntity.notFound().build(); // Si el pasajero no existe en el vuelo, devuelve 404 Not Found
                }
            } catch (Exception e) {
                return ResponseEntity.badRequest().build(); // Si ocurre algún otro error, devuelve 400 Bad Request
            }
        } else {
            return ResponseEntity.badRequest().build(); // Si el DNI no es válido, devuelve 400 Bad Request
        }
    }

    /**
     * Elimina un pasajero de un vuelo.
     *
     * @param id  ID del vuelo.
     * @param nif DNI del pasajero.
     * @return ResponseEntity con el resultado de la operación.
     */
    @DeleteMapping(path = "/passenger/{nif}")
    public ResponseEntity<Void> deletePassenger(@PathVariable("id") String id, @PathVariable("nif") String nif) {

        try {
            // Intenta eliminar al pasajero del vuelo
            if (passengerService.delete(id, nif)) {
                return ResponseEntity.ok().build(); // Si se elimina correctamente, devuelve 200 OK
            }
        } catch (NotExistFlight | NotExistPassenger e) {
            return ResponseEntity.notFound().build(); // Si el vuelo o el pasajero no existen, devuelve 404 Not Found
        }
        return ResponseEntity.badRequest().build(); // Si ocurre algún otro error, devuelve 400 Bad Request
    }
}


