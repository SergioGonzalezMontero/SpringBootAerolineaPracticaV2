package org.educa.airline.mappers;


import org.educa.airline.dto.LuggageDTO;
import org.educa.airline.entity.Luggage;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase encargada de mapear entre objetos Luggage y LuggageDTO.
 */
@Component
public class LuggageMapper {

    /**
     * Convierte un objeto LuggageDTO a un objeto Luggage.
     *
     * @param luggageDTO Objeto LuggageDTO a convertir.
     * @return Objeto Luggage resultante.
     */
    public Luggage toEntity(LuggageDTO luggageDTO) {
        Luggage luggage = new Luggage();
        luggage.setId(luggageDTO.getId());
        luggage.setNif(luggageDTO.getNif());
        luggage.setFlightId(luggageDTO.getFlightId());
        luggage.setDescription(luggageDTO.getDescription());
        return luggage;
    }

    /**
     * Convierte un objeto Luggage a un objeto LuggageDTO.
     *
     * @param luggage Objeto Luggage a convertir.
     * @return Objeto LuggageDTO resultante.
     */
    public LuggageDTO toDTO(Luggage luggage) {
        LuggageDTO luggageDTO = new LuggageDTO();
        luggageDTO.setId(luggage.getId());
        luggageDTO.setNif(luggage.getNif());
        luggageDTO.setFlightId(luggage.getFlightId());
        luggageDTO.setDescription(luggage.getDescription());
        return luggageDTO;
    }

    /**
     * Convierte una lista de objetos Luggage a una lista de objetos LuggageDTO.
     *
     * @param luggageList Lista de objetos Luggage a convertir.
     * @return Lista de objetos LuggageDTO resultante.
     */
    public List<LuggageDTO> toDTOs(List<Luggage> luggageList) {
        List<LuggageDTO> luggageDTOs = new ArrayList<>();
        for (Luggage luggage : luggageList) {
            LuggageDTO luggageDTO = toDTO(luggage);
            luggageDTOs.add(luggageDTO);
        }
        return luggageDTOs;
    }
}
