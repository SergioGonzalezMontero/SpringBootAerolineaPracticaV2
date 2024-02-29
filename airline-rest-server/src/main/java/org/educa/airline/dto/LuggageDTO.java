package org.educa.airline.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

/**
 * DTO (Data Transfer Object) para representar un equipaje.
 */

@Data
public class LuggageDTO {
    @NotNull
    @Positive
    private int id;

    @NotNull
    @NotEmpty
    @Pattern(regexp = "\\d{8}[A-Za-z]", message = "El NIF debe tener un formato válido")
    private String nif;

    @NotNull
    @NotEmpty
    private String flightId;

    @NotNull
    @NotEmpty
    @Size(max = 200)
    private String description;
}
