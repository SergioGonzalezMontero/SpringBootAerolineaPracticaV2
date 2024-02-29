package org.example.api;

/**
 * Esta clase abstracta proporciona funcionalidad básica para los servicios de API.
 * Contiene la URL base y una instancia de conexión para realizar solicitudes HTTP.
 */
public abstract class AppiService {

    // La URL base para las solicitudes a la API
    final String URL = "http://localhost:8080";

    // Instancia de conexión para realizar solicitudes HTTP
    final Connection connection = new Connection();
}
