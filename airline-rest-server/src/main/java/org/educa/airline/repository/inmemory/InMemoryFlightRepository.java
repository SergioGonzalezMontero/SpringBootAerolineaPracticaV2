package org.educa.airline.repository.inmemory;

import org.educa.airline.entity.Flight;
import org.educa.airline.repository.FlightRepository;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Repositorio en memoria para vuelos.
 */
@Component
public class InMemoryFlightRepository implements FlightRepository {

    // Mapa que almacena los vuelos, utilizando el ID del vuelo como clave y el objeto Flight como valor.
    private Map<String, Flight> flights = new HashMap<>();

    /**
     * Obtiene una lista de vuelos filtrada por origen y destino.
     *
     * @param origin      Origen del vuelo.
     * @param destination Destino del vuelo.
     * @return Lista de vuelos filtrada.
     */
    @Override
    public List<Flight> list(String origin, String destination) {
        return flights.values().stream()
                .filter(f -> origin == null || origin.equals(f.getOrigin()))
                .filter(f -> destination == null || destination.equals(f.getDestination()))
                .collect(Collectors.toList());
    }

    /**
     * Obtiene un vuelo por su ID.
     *
     * @param flightId ID del vuelo.
     * @return Objeto Flight correspondiente al ID proporcionado, o null si no se encuentra.
     */
    @Override
    public Flight getFlight(String flightId) {
        return flights.get(flightId);
    }

    /**
     * Agrega un vuelo al repositorio.
     *
     * @param flight Objeto Flight a agregar.
     * @return true si el vuelo se agregó correctamente, false si ya existe un vuelo con el mismo ID.
     */
    @Override
    public boolean add(Flight flight) {
        if (!flights.containsKey(flight.getId())) {
            flights.put(flight.getId(), flight);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Actualiza un vuelo en el repositorio.
     *
     * @param flightId ID del vuelo a actualizar.
     * @param flight   Objeto Flight actualizado.
     * @return true si el vuelo se actualizó correctamente, false si no se encontró el vuelo con el ID proporcionado.
     */
    @Override
    public boolean updateFlight(String flightId, Flight flight) {
        if (flights.containsKey(flightId)) {
            flights.remove(flightId);
            flights.put(flight.getId(), flight);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Elimina un vuelo del repositorio.
     *
     * @param flightId ID del vuelo a eliminar.
     * @return true si el vuelo se eliminó correctamente, false si no se encontró el vuelo con el ID proporcionado.
     */
    @Override
    public boolean delete(String flightId) {
        if (flights.containsKey(flightId)) {
            flights.remove(flightId);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Lista todos los vuelos almacenados en el repositorio.
     *
     * @return Lista de todos los vuelos.
     */
    public List<Flight> listAll() {
        return flights.values().stream().collect(Collectors.toList());
    }
}