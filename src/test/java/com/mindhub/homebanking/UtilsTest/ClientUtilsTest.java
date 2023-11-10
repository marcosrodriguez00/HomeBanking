package com.mindhub.homebanking.UtilsTest;

import org.junit.jupiter.api.Test;

import static com.mindhub.homebanking.utils.ClientUtils.isValidEmail;
import static org.junit.jupiter.api.Assertions.*;


public class ClientUtilsTest {

    //isValidEmail

    @Test
    void emailIsNotEmpty() {
        assertThrowsExactly(IllegalArgumentException.class, () -> isValidEmail(""), "email is empty");
    }

    @Test
    void emailContainsAt () {
        assertThrowsExactly(IllegalArgumentException.class, () -> isValidEmail("emailemail.com"), "email has to contain @");
    }
}
