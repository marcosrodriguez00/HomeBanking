package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dto.CardDTO;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface CardService {

    Card getCardById(Long id);

    CardDTO getCardDTOById(Long id);

    List<CardDTO> getAllCardDTO();

    List<Card> getAllCards();

    Boolean existsCardByTypeAndColorAndClientAndIsActive(CardType cardType, CardColor cardColor, Client client, boolean isActive);

    Boolean existsCardByNumber(String cardNumber);

    void saveCard(Card card);

    Card getCardByNumber(String number);

    void deleteCardByNumber(String number);

}
