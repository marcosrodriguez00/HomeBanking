package com.mindhub.homebanking.UtilsTest;

import com.mindhub.homebanking.utils.CardUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.mindhub.homebanking.utils.CardUtils.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class CardUtilsTest {
    // generateRandomDigit

    private int randomDigit;

    @BeforeEach
    void RandomDigit(){
        randomDigit = generateRandomDigit();
    }

    @Test
    void digitBelow0 () {
        assertThat(randomDigit, greaterThanOrEqualTo(0));
    }

    @Test
    void digitGreaterThan9 () {
        assertThat(randomDigit, lessThanOrEqualTo(9));
    }

    // generateCvv

    @Test
    void cvvLengthIs3 () {
        String cvv = generateCvv();
        assertThat(cvv.length(), equalTo(3));
    }

    // generateCardNumber

    private String cardNumber;

    @BeforeEach
    void createCardNumber(){
        cardNumber = generateCardNumber();
    }

    // ver que tenga el guion cada 4 numeros
    @Test
    void cardNumberHasHyphen(){
        StringBuilder extractedChars = new StringBuilder();

        for (int i = 4; i < cardNumber.length(); i = i + 5 ) {
            extractedChars.append(cardNumber.charAt(i));
        }
        assertThat(extractedChars.toString(), equalTo("---"));
    }

    // ver que tenga 16 numeros
    @Test
    void cardNumberHas16numbers() {
        String onlyNumbers = cardNumber.replace("-", "");

        assertThat(onlyNumbers.length(), equalTo(16));
    }
}
