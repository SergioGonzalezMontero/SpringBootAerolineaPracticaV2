package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Esta clase representa un objeto de transferencia de datos (DTO) para los pasajeros.
 * Contiene información sobre el NIF, ID del vuelo, nombre, apellido, correo electrónico y número de asiento del pasajero.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PassengerDTO {


    private String nif;

    private String flightId;

    private String name;

    private String surname;

    private String email;

    private int seatNumber;
}