package com.mindhub.homebanking.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ClientUtils {

    public static boolean isValidEmail(String email) {
        // Define la expresión regular para validar el formato de un correo electrónico
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";

        // Compila la expresión regular en un patrón
        Pattern pattern = Pattern.compile(emailRegex);

        // Crea un objeto Matcher para comparar el correo electrónico con el patrón
        Matcher matcher = pattern.matcher(email);

        // Verifica si el correo electrónico coincide con el patrón
        return matcher.matches();
    }
}
