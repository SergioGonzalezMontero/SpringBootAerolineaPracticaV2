package org.example.tools;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * Esta clase proporciona métodos de utilidad para realizar diversas validaciones.
 */
public class Tools {

    /**
     * Comprueba si una cadena representa un DNI válido.
     *
     * @param dni La cadena que se va a comprobar.
     * @return true si el DNI es válido, false de lo contrario.
     */
    public boolean esDNIValido(String dni) {
        // Verificar que la cadena tiene longitud 9 (8 dígitos + 1 letra)
        if (dni == null || dni.length() != 9)
            return false;

        // Extraer los dígitos y la letra
        String digitos = dni.substring(0, 8);
        char letra = Character.toUpperCase(dni.charAt(8));

        // Verificar si los primeros 8 caracteres son dígitos
        for (int i = 0; i < digitos.length(); i++) {
            if (!Character.isDigit(digitos.charAt(i)))
                return false;
        }

        // Calcular la letra correspondiente a los dígitos
        char letraCalculada = calcularLetra(digitos);

        // Comparar la letra calculada con la letra recibida
        return letra == letraCalculada;
    }

    /**
     * Calcula la letra correspondiente a un número de DNI.
     *
     * @param digitos Los dígitos del DNI.
     * @return La letra correspondiente.
     */
    private char calcularLetra(String digitos) {
        String letras = "TRWAGMYFPDXBNJZSQVHLCKE";
        int resto = Integer.parseInt(digitos) % 23;
        return letras.charAt(resto);
    }

    /**
     * Comprueba si una cadena representa una fecha válida en el formato yyyy-mm-dd.
     *
     * @param fechaStr La cadena que se va a comprobar.
     * @return true si la fecha es válida, false de lo contrario.
     */
    public boolean esFechaValida(String fechaStr) {
        try {
            // Intentar analizar la cadena como una fecha
            LocalDate fecha = LocalDate.parse(fechaStr);

            // La fecha se analizó correctamente, por lo que es válida
            return true;
        } catch (DateTimeParseException e) {
            // La cadena no pudo ser analizada como una fecha en el formato esperado
            return false;
        }
    }

    /**
     * Comprueba si una cadena representa un número válido, es decir, un número entero mayor que 0 y menor que 500.
     *
     * @param cadena La cadena que se va a comprobar.
     * @return true si la cadena es un número válido, false de lo contrario.
     */
    public boolean esNumeroValido(String cadena) {
        try {
            int numero = Integer.parseInt(cadena);
            return numero > 0 && numero < 500;
        } catch (NumberFormatException e) {
            // Si la cadena no se puede convertir a un número, entonces no es un número válido
            return false;
        }
    }

    public boolean esTextoValido(String texto) {
        return !texto.isEmpty();
    }

    /**
     * Confirma si el usuario desea realizar una acción.
     *
     * @param scanner El scanner para leer la entrada del usuario.
     * @return true si el usuario confirma con "s", false si responde con "n".
     */
    public boolean confirmarAccion(Scanner scanner) {
        System.out.println(" Si confirma la acción pulse S :");
        String respuesta = scanner.nextLine().trim().toUpperCase();
        return respuesta.equals("S");
    }

    public boolean contrasenaValida(String pass) {
        return pass.length() >= 4;
    }
}

