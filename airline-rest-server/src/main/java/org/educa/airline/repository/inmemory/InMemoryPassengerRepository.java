package org.educa.airline.repository.inmemory;


import org.educa.airline.entity.Passenger;
import org.educa.airline.repository.PassengerRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Repositorio en memoria para pasajeros.
 */
@Component
public class InMemoryPassengerRepository implements PassengerRepository {
    // Por cada vuelo, habrá un mapa de pasajeros
    private final Map<String, Map<String, Passenger>> passengers = new HashMap<>();

    /**
     * Obtiene una lista de todos los pasajeros de todos los vuelos.
     *
     * @return Lista de todos los pasajeros.
     */
    @Override
    public List<Passenger> listPassengers() {
        return passengers.values().stream()
                .flatMap(m -> m.values().stream())
                .collect(Collectors.toList());
    }

    /**
     * Obtiene una lista de todos los pasajeros de un vuelo específico.
     *
     * @param flightId ID del vuelo.
     * @return Lista de pasajeros del vuelo especificado.
     */
    @Override
    public List<Passenger> listPassengers(String flightId) {
        return new ArrayList<>(getFlightPassengers(flightId).values());
    }

    /**
     * Obtiene un pasajero específico de un vuelo.
     *
     * @param flightId ID del vuelo.
     * @param nif      NIF del pasajero.
     * @return Pasajero encontrado, o null si no existe.
     */
    @Override
    public Passenger getPassenger(String flightId, String nif) {
        return getFlightPassengers(flightId).get(nif);
    }

    /**
     * Verifica si un pasajero existe en un vuelo.
     *
     * @param flightId ID del vuelo.
     * @param nif      NIF del pasajero.
     * @return true si el pasajero existe en el vuelo, false en caso contrario.
     */
    @Override
    public boolean existPassenger(String flightId, String nif) {
        return getFlightPassengers(flightId).containsKey(nif);
    }

    /**
     * Elimina un pasajero de un vuelo.
     *
     * @param flightId ID del vuelo.
     * @param nif      NIF del pasajero.
     * @return true si el pasajero se eliminó correctamente, false si el pasajero no existía en el vuelo.
     */
    @Override
    public boolean deletePassenger(String flightId, String nif) {
        return getFlightPassengers(flightId).remove(nif) != null;
    }

    /**
     * Añade un pasajero a un vuelo.
     *
     * @param passenger Pasajero a añadir.
     * @return true si el pasajero se agregó correctamente, false si ya existe en el vuelo.
     */
    @Override
    public boolean addPassenger(Passenger passenger) {
        if (existPassenger(passenger.getFlightId(), passenger.getNif())) {
            return false;
        } else {
            getFlightPassengers(passenger.getFlightId()).put(passenger.getNif(), passenger);
            return true;
        }
    }

    /**
     * Actualiza la información de un pasajero en un vuelo.
     *
     * @param nif       NIF del pasajero a actualizar.
     * @param passenger Pasajero con la información actualizada.
     */
    @Override
    public void updatePassenger(String nif, Passenger passenger) {
        getFlightPassengers(passenger.getFlightId()).remove(nif);
        getFlightPassengers(passenger.getFlightId()).put(passenger.getNif(), passenger);
    }

    // Método privado para obtener el mapa de pasajeros de un vuelo específico.
    private Map<String, Passenger> getFlightPassengers(String flightId) {
        passengers.putIfAbsent(flightId, new HashMap<>());
        return passengers.get(flightId);
    }
}