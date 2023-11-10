package com.mindhub.homebanking.UtilsTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.mindhub.homebanking.utils.LoanUtils.dateFormatter;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoansUtilsTest {

    // dateFormatter

    @Test
    void formattedDateHasDesiredFormat() {
        LocalDateTime inputDateTime = LocalDateTime.of(2023, 11, 9, 13, 30, 0); // Fecha de ejemplo
        String expectedFormattedDateTime = "2023-11-09 13:30:00"; // Formato esperado

        LocalDateTime formattedDateTime = dateFormatter(inputDateTime);
        String actualFormattedDateTime = formattedDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        assertEquals(expectedFormattedDateTime, actualFormattedDateTime);
    }
}
