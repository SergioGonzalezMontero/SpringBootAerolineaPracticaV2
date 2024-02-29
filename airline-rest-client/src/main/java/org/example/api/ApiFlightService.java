package org.example.api;

import com.google.gson.Gson;
import org.example.dto.FlightDTO;


/**
 * Esta clase proporciona métodos para interactuar con la API de vuelos.
 */
public class ApiFlightService extends AppiService {
    // La URL base para las operaciones relacionadas con vuelos
    private final String URL = super.URL + "/flights";

    /**
     * Busca vuelos por origen y destino.
     *
     * @param origen  Origen del vuelo.
     * @param destino Destino del vuelo.
     * @return Un arreglo de objetos FlightDTO que coinciden con el origen y destino especificados.
     * @throws Exception Si ocurre un error durante la solicitud.
     */
    public FlightDTO[] findFlightOrigenDestiny(String origen, String destino) throws Exception {
        // Realiza una solicitud GET a la API con los parámetros de origen y destino
        String body = connection.doGet(URL + "?ori=" + origen + "&des=" + destino);
        Gson gson = new Gson();
        // Convierte la respuesta JSON en un arreglo de objetos FlightDTO
        FlightDTO[] flightDTO = gson.fromJson(body, FlightDTO[].class);
        return flightDTO;
    }

    /**
     * Crea un nuevo vuelo.
     *
     * @param flightDTO El objeto FlightDTO que representa el vuelo a crear.
     * @throws Exception Si ocurre un error durante la solicitud.
     */
    public void create(FlightDTO flightDTO) throws Exception {
        Gson gson = new Gson();
        // Convierte el objeto FlightDTO a JSON
        String body = gson.toJson(flightDTO);
        // Realiza una solicitud POST a la API para crear el vuelo
        connection.doPost(body, URL + "/create");
    }

    /**
     * Busca un vuelo por código y fecha.
     *
     * @param code El código del vuelo.
     * @param date La fecha del vuelo.
     * @return El objeto FlightDTO que corresponde al código y fecha especificados.
     * @throws Exception Si ocurre un error durante la solicitud.
     */
    public FlightDTO findflightCodeDate(String code, String date) throws Exception {
        // Realiza una solicitud GET a la API con el código y fecha del vuelo
        String body = connection.doGet(URL + "/" + code + "-" + date + "?date=" + date);
        Gson gson = new Gson();
        // Convierte la respuesta JSON en un objeto FlightDTO
        FlightDTO flightDTO = gson.fromJson(body, FlightDTO.class);
        return flightDTO;
    }

    /**
     * Elimina un vuelo por su ID.
     *
     * @param id El ID del vuelo a eliminar.
     * @return true si el vuelo se eliminó correctamente, false en caso contrario.
     */
    public boolean delete(String id) throws Exception {

        // Realiza una solicitud DELETE a la API para eliminar el vuelo
        connection.doDelete(URL + "/" + id);
        return true;

    }

    /**
     * Obtiene todos los vuelos.
     *
     * @return Un arreglo de objetos FlightDTO que representan todos los vuelos disponibles.
     * @throws Exception Si ocurre un error durante la solicitud.
     */
    public FlightDTO[] findAll() throws Exception {
        // Realiza una solicitud GET a la API para obtener todos los vuelos
        String body = connection.doGet(URL + "/allflights");
        Gson gson = new Gson();
        // Convierte la respuesta JSON en un arreglo de objetos FlightDTO
        FlightDTO[] flightDTO = gson.fromJson(body, FlightDTO[].class);
        return flightDTO;
    }
}