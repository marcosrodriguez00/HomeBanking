package com.mindhub.homebanking.utils;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public final class CardUtils {

    public static int generateRandomDigit() {
        Random random = new Random();
        return random.nextInt(10); // Genera un número aleatorio entre 0 y 9
    }

    public static String generateCvv() {
        List<Integer> cvv = Arrays.asList(null, null, null);

        for(int i = 0; i < 3; i++) {
            cvv.set(i, generateRandomDigit());
        }

        StringBuilder builder = new StringBuilder();

        for (Integer num : cvv) {
            builder.append(num);
        }

        return builder.toString();
    }

    public static String generateCardNumber() {
        StringBuilder numeroTarjeta = new StringBuilder();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                numeroTarjeta.append(generateRandomDigit());
            }
            if (i < 3) {
                numeroTarjeta.append("-"); // Agrega el guion entre grupos de dígitos
            }
        }

        return numeroTarjeta.toString();
    }
}
