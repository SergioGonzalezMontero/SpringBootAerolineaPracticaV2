package org.educa.airline.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * DTO (Data Transfer Object) para representar un vuelo.
 */
@Data
public class FlightDTO {
    @NotNull
    @NotEmpty
    private String id;

    @NotNull
    @NotEmpty
    private String code;

    @NotNull
    @NotEmpty
    private String origin;

    @NotNull
    @NotEmpty
    private String destination;

    @NotNull
    private String date;
}