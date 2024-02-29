package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Esta clase representa un objeto de transferencia de datos (DTO) para los vuelos.
 * Contiene información sobre el código, origen, destino, fecha y ID del vuelo.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlightDTO {

    private String code;

    private String origin;

    private String destination;

    private String date;

    private String id;
}
