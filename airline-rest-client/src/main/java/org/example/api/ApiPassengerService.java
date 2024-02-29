package org.example.api;

import com.google.gson.Gson;
import org.example.dto.PassengerDTO;

/**
 * Esta clase proporciona métodos para interactuar con la API de pasajeros.
 */
public class ApiPassengerService extends AppiService {

    // La URL base para las operaciones relacionadas con pasajeros
    private final String URL = super.URL + "/flights";

    /**
     * Crea un nuevo pasajero para un vuelo específico.
     *
     * @param passengerDTO El objeto PassengerDTO que representa al pasajero a crear.
     * @throws Exception Si ocurre un error durante la solicitud.
     */
    public void create(PassengerDTO passengerDTO) throws Exception {
        Gson gson = new Gson();
        // Convierte el objeto PassengerDTO a JSON
        String body = gson.toJson(passengerDTO);
        // Realiza una solicitud POST a la API para crear el pasajero en el vuelo correspondiente
        connection.doPost(body, URL + "/" + passengerDTO.getFlightId() + "/passenger");
    }

    /**
     * Busca todos los pasajeros de un vuelo específico.
     *
     * @param id El ID del vuelo.
     * @return Un arreglo de objetos PassengerDTO que representan todos los pasajeros del vuelo.
     * @throws Exception Si ocurre un error durante la solicitud.
     */
    public PassengerDTO[] findPassengersOfFlight(String id) throws Exception {
        // Realiza una solicitud GET a la API para obtener todos los pasajeros del vuelo
        String body = connection.doGet(URL + "/" + id + "/passengers");
        Gson gson = new Gson();
        // Convierte la respuesta JSON en un arreglo de objetos PassengerDTO
        return gson.fromJson(body, PassengerDTO[].class);
    }

    /**
     * Busca un pasajero específico de un vuelo.
     *
     * @param id  El ID del vuelo.
     * @param nif El NIF del pasajero.
     * @return El objeto PassengerDTO que representa al pasajero buscado.
     * @throws Exception Si ocurre un error durante la solicitud.
     */
    public PassengerDTO findPassengerOfFlight(String id, String nif) throws Exception {
        // Realiza una solicitud GET a la API para obtener el pasajero del vuelo
        String body = connection.doGet(URL + "/" + id + "/passenger/" + nif);
        Gson gson = new Gson();
        // Convierte la respuesta JSON en un objeto PassengerDTO
        return gson.fromJson(body, PassengerDTO.class);
    }

    /**
     * Actualiza los detalles de un pasajero en un vuelo.
     *
     * @param passengerDTO El objeto PassengerDTO con los detalles actualizados del pasajero.
     * @param id           El ID del vuelo.
     * @param nif          El NIF del pasajero.
     * @return El objeto PassengerDTO actualizado.
     */
    public PassengerDTO updatePassengerOfFlight(PassengerDTO passengerDTO, String id, String nif) throws Exception {
        Gson gson = new Gson();
        String body = gson.toJson(passengerDTO);

        // Realiza una solicitud PUT a la API para actualizar los detalles del pasajero en el vuelo
        connection.doUpdate(body, URL + "/" + id + "/passenger/" + nif);

        return gson.fromJson(body, PassengerDTO.class);
    }

    /**
     * Elimina un pasajero de un vuelo.
     *
     * @param passengerDTO El objeto PassengerDTO que representa al pasajero a eliminar.
     * @param id           El ID del vuelo.
     * @param nif          El NIF del pasajero.
     * @return true si el pasajero se eliminó correctamente, false en caso contrario.
     */
    public boolean deletePassangerOfFlight(PassengerDTO passengerDTO, String id, String nif) throws Exception {
        Gson gson = new Gson();
        String body = gson.toJson(passengerDTO);

        // Realiza una solicitud DELETE a la API para eliminar el pasajero del vuelo
        connection.doDelete(URL + "/" + id + "/passenger/" + nif);
        return true;

    }
}