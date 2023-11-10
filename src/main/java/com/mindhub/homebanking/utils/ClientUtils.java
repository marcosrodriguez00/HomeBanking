package com.mindhub.homebanking.utils;

public final class ClientUtils {

    public static boolean isValidEmail(String email) throws IllegalArgumentException{

        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("Email is invalid");
        }

        return true;
    }
}
