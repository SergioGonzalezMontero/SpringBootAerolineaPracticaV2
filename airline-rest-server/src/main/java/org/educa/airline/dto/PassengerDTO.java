package org.educa.airline.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

/**
 * DTO (Data Transfer Object) para representar un pasajero.
 */
@Data
public class PassengerDTO {
    @NotNull
    @NotEmpty
    private String nif;

    @NotNull
    @NotEmpty
    private String flightId;

    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @NotEmpty
    private String surname;

    @NotNull
    @NotEmpty
    @Email
    private String email;

    @NotNull
    @Positive
    private int seatNumber;
}
