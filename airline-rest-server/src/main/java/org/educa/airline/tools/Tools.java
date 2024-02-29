package org.educa.airline.tools;

import org.springframework.stereotype.Component;

@Component
public class Tools {
    // Método para comprobar si una cadena es un DNI válido
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

    // Método para calcular la letra del DNI a partir de los dígitos
    private char calcularLetra(String digitos) {
        String letras = "TRWAGMYFPDXBNJZSQVHLCKE";
        int resto = Integer.parseInt(digitos) % 23;
        return letras.charAt(resto);
    }
}
