package org.educa.airline.mappers;

import org.educa.airline.dto.FlightDTO;
import org.educa.airline.entity.Flight;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Clase encargada de mapear entre objetos Flight y FlightDTO.
 */
@Component
public class FlightMapper {

    /**
     * Convierte un objeto FlightDTO a un objeto Flight.
     *
     * @param flightDTO Objeto FlightDTO a convertir.
     * @return Objeto Flight resultante.
     */
    public Flight toEntity(FlightDTO flightDTO) {
        Flight flight = new Flight();
        flight.setCode(flightDTO.getCode());
        flight.setOrigin(flightDTO.getOrigin());
        flight.setDestination(flightDTO.getDestination());
        flight.setDate(stringToDate(flightDTO.getDate()));
        flight.setId(flightDTO.getId());
        return flight;
    }

    /**
     * Convierte un objeto Flight a un objeto FlightDTO.
     *
     * @param flight Objeto Flight a convertir.
     * @return Objeto FlightDTO resultante.
     */
    public FlightDTO toDTO(Flight flight) {
        FlightDTO flightDTO = new FlightDTO();
        flightDTO.setCode(flight.getCode());
        flightDTO.setOrigin(flight.getOrigin());
        flightDTO.setDestination(flight.getDestination());
        flightDTO.setDate(dateToString(flight.getDate()));
        flightDTO.setId(flight.getId());
        return flightDTO;
    }

    /**
     * Convierte una lista de objetos Flight a una lista de objetos FlightDTO.
     *
     * @param flightList Lista de objetos Flight a convertir.
     * @return Lista de objetos FlightDTO resultante.
     */
    public List<FlightDTO> toDTOs(List<Flight> flightList) {
        List<FlightDTO> flightDTOs = new ArrayList<>();
        for (Flight flight : flightList) {
            FlightDTO flightDTO = toDTO(flight);
            flightDTOs.add(flightDTO);
        }
        return flightDTOs;
    }

    /**
     * Convierte una fecha en formato String a un objeto Date.
     *
     * @param dateString Fecha en formato String.
     * @return Objeto Date resultante.
     */
    public Date stringToDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            System.out.println("El formato de fecha no es válido (yyyy-MM-dd)");
            throw new RuntimeException(e);
        }
    }

    /**
     * Convierte un objeto Date a una cadena en formato String.
     *
     * @param date Objeto Date a convertir.
     * @return Cadena en formato String resultante.
     */
    public String dateToString(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }
}
