package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.dto.CardDTO;
import com.mindhub.homebanking.dto.ClientDTO;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class CardImplement implements CardService {

    @Autowired
    CardRepository cardRepository;

    @Override
    public Card getCardById(Long id) {
        return cardRepository.findById(id).orElse(null);
    }

    @Override
    public CardDTO getCardDTOById(Long id) {
        return new CardDTO(getCardById(id));
    }

    @Override
    public List<Card> getAllCards() {
        return cardRepository.findAll();
    }

    @Override
    public List<CardDTO> getAllCardDTO() {
        return getAllCards().stream().map(CardDTO::new).collect(toList());
    }

    @Override
    public Boolean existsCardByTypeAndColorAndClient(CardType cardType, CardColor cardColor, Client client) {
        return cardRepository.existsByTypeAndColorAndClient(cardType, cardColor, client);
    }

    @Override
    public Boolean existsCardByNumber(String cardNumber) {
        return cardRepository.existsByNumber(cardNumber);
    }

    @Override
    public void saveCard(Card card) {
        cardRepository.save(card);
    }
}
