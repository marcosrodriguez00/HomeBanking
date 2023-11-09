package com.mindhub.homebanking.Controllers;

import com.mindhub.homebanking.dto.CardDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.services.CardService;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static com.mindhub.homebanking.utils.CardUtils.*;

@RestController
@RequestMapping("/api")
public class CardController {

    @Autowired
    CardService cardService;

    @Autowired
    ClientService clientService;

    @GetMapping("/cards")
    public List<CardDTO> getAllCards() {
        return cardService.getAllCardDTO();
    }

    @GetMapping("/cards/{id}")
    public CardDTO getCard(@PathVariable Long id) { // PathVariable se usa para que el metodo getAccount tome el valor de {id}
        return cardService.getCardDTOById(id);
    }

    @PostMapping("/clients/current/cards")
    public ResponseEntity<Object> newCard(Authentication authentication,
                                          @RequestParam CardType cardType, @RequestParam CardColor cardColor) {

        // guardo la info en el cliente
        Client client = clientService.getClientByEmail(authentication.getName());

        if ( cardService.existsCardByTypeAndColorAndClient(cardType, cardColor, client) ) {
            return new ResponseEntity<>("You already have that card!", HttpStatus.FORBIDDEN);
        }

        String cardNumber = generateCardNumber();

        while ( cardService.existsCardByNumber(cardNumber) ) {
            cardNumber = generateCardNumber();
        }

        // Creo la cuenta nueva y la agrego al cliente
        Card card = new Card(client.fullName(), cardNumber, generateCvv(), cardType, cardColor, LocalDate.now().plusYears(5), LocalDate.now());
        client.addCard(card);

        // guardo el cliente con la nueva cuenta y devuelvo respuesta exitosa
        clientService.saveClient(client);
        cardService.saveCard(card);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
