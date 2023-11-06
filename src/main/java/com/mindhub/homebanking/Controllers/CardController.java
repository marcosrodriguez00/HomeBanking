package com.mindhub.homebanking.Controllers;

import com.mindhub.homebanking.dto.AccountDTO;
import com.mindhub.homebanking.dto.CardDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.CardService;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class CardController {

    @Autowired
    CardService cardService;

    @Autowired
    ClientService clientService;

    @RequestMapping("/cards")
    public List<CardDTO> getAllCards() {
        return cardService.getAllCardDTO();
    }

    @RequestMapping("/cards/{id}")
    public CardDTO getCard(@PathVariable Long id) { // PathVariable se usa para que el metodo getAccount tome el valor de {id}
        return cardService.getCardDTOById(id);
    }

    public int generarDigitoAleatorio() {
        Random random = new Random();
        return random.nextInt(10); // Genera un número aleatorio entre 0 y 9
    }

    public String generateCvv() {
        List<Integer> cvv = Arrays.asList(null, null, null);

        for(int i = 0; i < 3; i++) {
            cvv.set(i, generarDigitoAleatorio());
        }

        StringBuilder builder = new StringBuilder();

        for (Integer num : cvv) {
            builder.append(num);
        }

        return builder.toString();
    }

    public String generateCardNumber() {
        StringBuilder numeroTarjeta = new StringBuilder();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                numeroTarjeta.append(generarDigitoAleatorio());
            }
            if (i < 3) {
                numeroTarjeta.append("-"); // Agrega el guion entre grupos de dígitos
            }
        }

        return numeroTarjeta.toString();
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
