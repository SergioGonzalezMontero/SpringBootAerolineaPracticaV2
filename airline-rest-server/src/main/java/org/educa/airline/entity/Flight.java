package org.educa.airline.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Clase que representa un vuelo.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Flight {
    private String code;
    private String origin;
    private String destination;
    private Date date;
    private String id;

}
