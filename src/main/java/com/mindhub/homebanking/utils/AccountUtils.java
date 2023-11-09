package com.mindhub.homebanking.utils;

public final class AccountUtils {

    public static String randomAccountNumber() {
        int randomNumber = (int) (Math.random() * (99999999 - 11111111) + 11111111);
        return "VIN-" + randomNumber;
    }
}
