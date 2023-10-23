package com.mindhub.homebanking.Controllers;

import com.mindhub.homebanking.dto.AccountDTO;
import com.mindhub.homebanking.dto.CardDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
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
    CardRepository cardRepository;

    @Autowired
    ClientRepository clientRepository;

    @RequestMapping("/cards")
    public List<CardDTO> getAllCards() {
        return cardRepository.findAll().stream().map(CardDTO::new).collect(toList());
    }

    @RequestMapping("/cards/{id}")
    public CardDTO getCard(@PathVariable Long id) { // PathVariable se usa para que el metodo getAccount tome el valor de {id}
        return cardRepository.findById(id)
                .map(CardDTO::new) // Convierte el cliente a un DTO
                .orElse(null); // Si no se encuentra, retorna null
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
                                          @RequestParam TransactionType type, @RequestParam CardColor color) {

        // encapsulo el cliente
        Client client = clientRepository.findByEmail(authentication.getName());

        // controlo que no haya mas de 3 cuentas
        if (client.getCards().size() >= 3) {
            return new ResponseEntity<>("Cannot create any more cards for this client", HttpStatus.FORBIDDEN);
        }

        String cardNumber = generateCardNumber();

        while (cardRepository.existsByNumber(cardNumber)) {
            cardNumber = generateCardNumber();
        }

        // Creo la cuenta nueva y la agrego al cliente
        Card card = new Card(client.fullName(), cardNumber, generateCvv(), type, color, LocalDate.now().plusYears(5), LocalDate.now());
        client.addCard(card);

        // guardo el cliente con la nueva cuenta y devuelvo respuesta exitosa
        clientRepository.save(client);
        cardRepository.save(card);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
