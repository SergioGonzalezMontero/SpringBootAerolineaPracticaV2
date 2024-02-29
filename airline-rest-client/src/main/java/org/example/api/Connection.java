package org.example.api;

import org.example.core.Client;
import org.example.exception.BadRequestException;
import org.example.exception.NotFoundException;
import org.example.exception.NotLoggedCorretlyException;
import org.example.exception.NotPermissException;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;

/**
 * Esta clase proporciona métodos para realizar solicitudes HTTP a una API.
 * Contiene métodos para realizar solicitudes GET, POST, PUT y DELETE.
 */
public class Connection {

    private static String getBasicAuthenticationHeader(String username, String password) {
        String valueToEncode = username + ":" + password;
        return "Basic " + Base64.getEncoder().encodeToString(valueToEncode.getBytes());
    }

    /**
     * Realiza una solicitud GET a la URL especificada.
     *
     * @param url La URL a la que se realizará la solicitud GET.
     * @return La respuesta de la solicitud.
     * @throws Exception Si ocurre un error durante la solicitud.
     */
    public String doGet(String url) throws Exception {
        // Realiza la solicitud GET
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .header("Authorization", getBasicAuthenticationHeader(Client.userLogged.getUsername(), Client.userLogged.getPassword()))
                .build();

        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Verifica el código de estado de la respuesta y maneja los errores correspondientes
            if (response.statusCode() == 200) {
                return response.body();
            } else if (response.statusCode() == 404) {
                throw new NotFoundException();
            } else if (response.statusCode() == 400) {
                throw new BadRequestException();
            } else if (response.statusCode() == 401) {
                throw new NotLoggedCorretlyException();
            } else if (response.statusCode() == 403) {
                throw new NotPermissException();
            } else {
                throw new Exception("Error: " + response.statusCode());
            }
        }
    }

    /**
     * Realiza una solicitud GET a la URL especificada.
     *
     * @param url La URL a la que se realizará la solicitud GET.
     * @return La respuesta de la solicitud.
     * @throws Exception Si ocurre un error durante la solicitud.
     */
    public String doGet(String url, String username, String password) throws Exception {
        // Realiza la solicitud GET
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .header("Authorization", getBasicAuthenticationHeader(username, password))
                .build();

        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Verifica el código de estado de la respuesta y maneja los errores correspondientes
            if (response.statusCode() == 200) {
                return response.body();
            } else if (response.statusCode() == 404) {
                throw new NotFoundException();
            } else if (response.statusCode() == 400) {
                throw new BadRequestException();
            } else if (response.statusCode() == 401) {
                throw new NotLoggedCorretlyException();
            } else if (response.statusCode() == 403) {
                throw new NotPermissException();
            } else {
                throw new Exception("Error: " + response.statusCode());
            }
        }
    }

    /**
     * Realiza una solicitud POST a la URL especificada con el cuerpo proporcionado.
     *
     * @param body El cuerpo de la solicitud POST.
     * @param url  La URL a la que se realizará la solicitud POST.
     * @throws Exception Si ocurre un error durante la solicitud.
     */
    public void doPost(String body, String url) throws Exception {

        // Realiza la solicitud POST
        if (Client.userLogged != null) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .header("Content-Type", "application/json")
                    .header("Authorization", getBasicAuthenticationHeader(Client.userLogged.getUsername(), Client.userLogged.getPassword()))
                    .build();

            try (HttpClient client = HttpClient.newHttpClient()) {
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());


                // Verifica el código de estado de la respuesta y maneja los errores correspondientes
                if (response.statusCode() == 201) {
                    System.out.println("Creado correctamente");
                } else if (response.statusCode() == 404) {
                    throw new NotFoundException();
                } else if (response.statusCode() == 400) {
                    throw new BadRequestException();
                } else if (response.statusCode() == 401) {
                    throw new NotLoggedCorretlyException();
                } else if (response.statusCode() == 403) {
                    throw new NotPermissException();
                } else {
                    throw new Exception("Error: " + response.statusCode());
                }
            }
        } else {

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .header("Content-Type", "application/json")
                    .build();

            try (HttpClient client = HttpClient.newHttpClient()) {
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());


                // Verifica el código de estado de la respuesta y maneja los errores correspondientes
                if (response.statusCode() == 200) {
                    System.out.println("Creado correctamente");
                } else if (response.statusCode() == 404) {
                    throw new NotFoundException();
                } else if (response.statusCode() == 400) {
                    throw new BadRequestException();
                } else if (response.statusCode() == 401) {
                    throw new NotLoggedCorretlyException();
                } else if (response.statusCode() == 403) {
                    throw new NotPermissException();
                } else {
                    throw new Exception("Error: " + response.statusCode());
                }
            }
        }
    }

    /**
     * Realiza una solicitud PUT a la URL especificada con el cuerpo proporcionado.
     *
     * @param body El cuerpo de la solicitud PUT.
     * @param url  La URL a la que se realizará la solicitud PUT.
     * @return La respuesta de la solicitud.
     * @throws Exception Si ocurre un error durante la solicitud.
     */
    public String doUpdate(String body, String url) throws Exception {
        // Realiza la solicitud PUT
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .PUT(HttpRequest.BodyPublishers.ofString(body))
                .header("Content-Type", "application/json")
                .header("Authorization", getBasicAuthenticationHeader(Client.userLogged.getUsername(), Client.userLogged.getPassword()))
                .build();

        try (HttpClient client = HttpClient.newHttpClient()) {
            System.out.println("Se va a enviar...");
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("...Se envio");

            // Verifica el código de estado de la respuesta y maneja los errores correspondientes
            if (response.statusCode() == 200) {
                return response.body();
            } else if (response.statusCode() == 404) {
                throw new NotFoundException();
            } else if (response.statusCode() == 400) {
                throw new BadRequestException();
            } else if (response.statusCode() == 401) {
                throw new NotLoggedCorretlyException();
            } else if (response.statusCode() == 403) {
                throw new NotPermissException();
            } else {
                System.out.println(response.statusCode());
                throw new Exception("Error: " + response.statusCode());
            }
        }
    }

    /**
     * Realiza una solicitud DELETE a la URL especificada.
     *
     * @param url La URL a la que se realizará la solicitud DELETE.
     * @return La respuesta de la solicitud.
     * @throws Exception Si ocurre un error durante la solicitud.
     */
    public String doDelete(String url) throws Exception {
        // Realiza la solicitud DELETE
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .DELETE()
                .header("Authorization", getBasicAuthenticationHeader(Client.userLogged.getUsername(), Client.userLogged.getPassword()))
                .build();

        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());


            // Verifica el código de estado de la respuesta y maneja los errores correspondientes
            if (response.statusCode() == 200) {
                return response.body();
            } else if (response.statusCode() == 404) {
                throw new NotFoundException();
            } else if (response.statusCode() == 400) {
                throw new BadRequestException();
            } else if (response.statusCode() == 401) {
                throw new NotLoggedCorretlyException();
            } else if (response.statusCode() == 403) {
                throw new NotPermissException();
            } else {
                throw new Exception("Error: " + response.statusCode());
            }
        }
    }
}
