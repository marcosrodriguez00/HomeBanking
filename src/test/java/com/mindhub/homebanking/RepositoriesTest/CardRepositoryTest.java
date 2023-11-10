package com.mindhub.homebanking.RepositoriesTest;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.repositories.CardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CardRepositoryTest {

    @Autowired
    CardRepository cardRepository;
    private List<Card> cards;

    @BeforeEach
    public void getCards() {
        cards = cardRepository.findAll();
    }

    @Test
    public void existsCards() {
        assertThat(cards, is(not(empty())));
    }

    @Test
    public void noCardsWithSameNumber() {
        Set<String> cardNumbers = cards.stream()
                .map(Card::getNumber)
                .collect(Collectors.toSet());

        assertEquals("La cantidad de tarjetas es igual a la cantidad de números únicos",
                cardNumbers.size(), cards.size());
    }
}
