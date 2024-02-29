package org.educa.airline.mappers;

import org.educa.airline.dto.PassengerDTO;
import org.educa.airline.entity.Passenger;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase encargada de mapear entre objetos Passenger y PassengerDTO.
 */
@Component
public class PassengerMapper {

    /**
     * Convierte un objeto PassengerDTO a un objeto Passenger.
     *
     * @param passengerDTO Objeto PassengerDTO a convertir.
     * @return Objeto Passenger resultante.
     */
    public Passenger toEntity(PassengerDTO passengerDTO) {
        Passenger passenger = new Passenger();
        passenger.setNif(passengerDTO.getNif());
        passenger.setFlightId(passengerDTO.getFlightId());
        passenger.setName(passengerDTO.getName());
        passenger.setSurname(passengerDTO.getSurname());
        passenger.setEmail(passengerDTO.getEmail());
        passenger.setSeatNumber(passengerDTO.getSeatNumber());
        return passenger;
    }

    /**
     * Convierte un objeto Passenger a un objeto PassengerDTO.
     *
     * @param passenger Objeto Passenger a convertir.
     * @return Objeto PassengerDTO resultante.
     */
    public PassengerDTO toDTO(Passenger passenger) {
        PassengerDTO passengerDTO = new PassengerDTO();
        passengerDTO.setNif(passenger.getNif());
        passengerDTO.setFlightId(passenger.getFlightId());
        passengerDTO.setName(passenger.getName());
        passengerDTO.setSurname(passenger.getSurname());
        passengerDTO.setEmail(passenger.getEmail());
        passengerDTO.setSeatNumber(passenger.getSeatNumber());
        return passengerDTO;
    }

    /**
     * Convierte una lista de objetos Passenger a una lista de objetos PassengerDTO.
     *
     * @param passengerList Lista de objetos Passenger a convertir.
     * @return Lista de objetos PassengerDTO resultante.
     */
    public List<PassengerDTO> toDTOs(List<Passenger> passengerList) {
        List<PassengerDTO> passengerDTOs = new ArrayList<>();
        for (Passenger passenger : passengerList) {
            PassengerDTO passengerDTO = toDTO(passenger);
            passengerDTOs.add(passengerDTO);
        }
        return passengerDTOs;
    }
}