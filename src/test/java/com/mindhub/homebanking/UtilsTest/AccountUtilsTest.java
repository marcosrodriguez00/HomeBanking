package com.mindhub.homebanking.UtilsTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.mindhub.homebanking.utils.AccountUtils.randomAccountNumber;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AccountUtilsTest {

    // randomAccountNumber
    private String accountNumber;

    @BeforeEach
    void generateRandomAccountNumber() {
        accountNumber = randomAccountNumber();
    }
    @Test
    void numberWithoutVIN() {
        assertTrue(accountNumber.startsWith("VIN"));
    }

    @Test
    void numberWithMoreThan8Figures() {
        String numberPart = accountNumber.replace("VIN-", "");

        assertTrue(numberPart.length() <= 8);
    }

    @Test
    void numberWithLessThan3Figures() {
        String numberPart = accountNumber.replace("VIN-", "");

        assertTrue(numberPart.length() >= 3);
    }
}
