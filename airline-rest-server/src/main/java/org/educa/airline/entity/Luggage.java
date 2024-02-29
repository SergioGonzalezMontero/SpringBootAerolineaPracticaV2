package org.educa.airline.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase que representa un equipaje.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Luggage {
    private int id;
    private String nif;
    private String flightId;
    private String description;
}
