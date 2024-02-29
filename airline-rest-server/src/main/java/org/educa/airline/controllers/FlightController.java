package org.educa.airline.controllers;

import jakarta.validation.Valid;
import org.educa.airline.dto.FlightDTO;
import org.educa.airline.entity.Flight;
import org.educa.airline.exceptions.BadDateException;
import org.educa.airline.mappers.FlightMapper;
import org.educa.airline.services.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Controlador para gestionar los vuelos.
 */
@RestController
@RequestMapping(path = "/flights")
public class FlightController {

    private final FlightService flightService;
    private final FlightMapper flightMapper;

    /**
     * Constructor del controlador.
     *
     * @param flightService Servicio de vuelos.
     * @param flightMapper  Mapeador de vuelos.
     */
    @Autowired
    public FlightController(FlightService flightService,
                            FlightMapper flightMapper) {
        this.flightService = flightService;
        this.flightMapper = flightMapper;
    }

    /**
     * Busca todos los vuelos con un origen y destino específicos.
     *
     * @param origin      Origen del vuelo.
     * @param destination Destino del vuelo.
     * @return ResponseEntity con la lista de vuelos encontrados.
     */
    @GetMapping()
    public ResponseEntity<List<FlightDTO>> findAllFlight(@RequestParam(value = "ori") String origin, @RequestParam(value = "des") String destination) {
        List<FlightDTO> flightDTOs = flightMapper.toDTOs(
                flightService.findAllFlight(origin, destination));
        if (!flightDTOs.isEmpty()) {
            return ResponseEntity.ok(flightDTOs); // Si se encuentran vuelos, se devuelve una lista de FlightDTO
        } else {
            return ResponseEntity.notFound().build(); // Si no se encuentran vuelos, se devuelve un código 404 Not Found
        }
    }

    /**
     * Busca un vuelo por su ID en una fecha específica.
     *
     * @param id   ID del vuelo.
     * @param date Fecha de búsqueda.
     * @return ResponseEntity con el vuelo encontrado.
     */
    @GetMapping(path = "/{id}")
    public ResponseEntity<FlightDTO> findFlightByID(@PathVariable("id") String id, @RequestParam(value = "date") String date) {

        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date dateCasted = castStringDate(date, formato); // Convierte la fecha de String a Date
            Flight flight = flightService.findFlightByIdDate(id, dateCasted); // Busca el vuelo por ID y fecha
            if (flight != null) {
                return ResponseEntity.ok(flightMapper.toDTO(flight)); // Si se encuentra el vuelo, se devuelve como FlightDTO
            } else {
                return ResponseEntity.notFound().build(); // Si no se encuentra el vuelo, se devuelve un código 404 Not Found
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build(); // Si ocurre un error en el formato de la fecha, se devuelve un código 400 Bad Request
        }
    }

    /**
     * Método privado para convertir una fecha de String a Date.
     *
     * @param date    Fecha en formato String.
     * @param formato Formato de la fecha.
     * @return Objeto Date.
     * @throws BadDateException Excepción si la fecha es inválida.
     */
    private Date castStringDate(String date, SimpleDateFormat formato) throws BadDateException {
        try {
            Date fechaDate = formato.parse(date);
            return fechaDate;

        } catch (ParseException e) {
            throw new BadDateException(); // Lanza una excepción personalizada si hay un error en el formato de la fecha
        }
    }

    /**
     * Crea un nuevo vuelo.
     *
     * @param flightDTO Datos del vuelo a crear.
     * @return ResponseEntity con el resultado de la operación.
     */
    @PostMapping(path = "/create")
    public ResponseEntity<Void> create(@RequestBody @Valid FlightDTO flightDTO) {
        try {
            Flight flight = flightMapper.toEntity(flightDTO); // Convierte el DTO de vuelo a entidad de vuelo
            if (flightService.create(flight)) {
                // Si se crea correctamente el vuelo, se devuelve un código 201 Created
                return ResponseEntity.status(201).build();
            } else {
                // Si ya existe un vuelo con la misma información, se devuelve un código 409 Conflict
                return ResponseEntity.status(409).build();
            }
        } catch (Exception e) {
            // Si ocurre algún otro error, se devuelve un código 400 Bad Request
            return ResponseEntity.badRequest().build();
        }
    }

    /* Método para actualizar un vuelo
    @PutMapping(path = "/{id}")
    public ResponseEntity<Void> update(@PathVariable("id") String id,
                                       @RequestBody @Valid FlightDTO flightDTO) {
        Flight flight = flightMapper.toEntity(flightDTO);
        try {
            if (flightService.update(flight, id)) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.badRequest().build();
            }
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    */

    /**
     * Elimina un vuelo por su ID.
     *
     * @param id ID del vuelo a eliminar.
     * @return ResponseEntity con el resultado de la operación.
     */
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        try {
            if (flightService.delete(id)) {
                // Si se elimina correctamente el vuelo, se devuelve un código 200 OK
                return ResponseEntity.ok().build();
            } else {
                // Si no se puede eliminar el vuelo, se devuelve un código 400 Bad Request
                return ResponseEntity.badRequest().build();
            }
        } catch (Exception e) {
            // Si ocurre algún otro error, se devuelve un código 404 Not Found
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Obtiene todos los vuelos.
     *
     * @return ResponseEntity con la lista de vuelos encontrados.
     */
    @GetMapping(path = "/allflights")
    public ResponseEntity<List<FlightDTO>> getAllFlight() {
        List<FlightDTO> flightDTOs = flightMapper.toDTOs(
                flightService.findAllFlight());
        if (!flightDTOs.isEmpty()) {
            return ResponseEntity.ok(flightDTOs); // Si se encuentran vuelos, se devuelve una lista de FlightDTO
        } else {
            return ResponseEntity.badRequest().build(); // Si no se encuentran vuelos, se devuelve un código 400 Bad Request
        }

    }
}
