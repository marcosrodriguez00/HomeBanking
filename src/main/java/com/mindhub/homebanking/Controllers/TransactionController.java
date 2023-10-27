package com.mindhub.homebanking.Controllers;

import com.mindhub.homebanking.dto.TransactionDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class TransactionController {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ClientRepository clientRepository;

    @RequestMapping("/transactions")
    public List<TransactionDTO> getAllTransactions() {
        return transactionRepository.findAll().stream().map(TransactionDTO::new).collect(toList());
    }

    @RequestMapping("/transactions/{id}")
    public TransactionDTO getTransaction(@PathVariable Long id) {
        return transactionRepository.findById(id)
                .map(TransactionDTO::new) // Convierte el cliente a un DTO
                .orElse(null); // Si no se encuentra, retorna null
    }

    @Transactional
    @RequestMapping(path = "/transactions", method = RequestMethod.POST)
    public ResponseEntity<Object> transaction (
            Authentication authentication,
            @RequestParam Double ammount, @RequestParam String description,
            @RequestParam String originAccountNumber, @RequestParam String destinyAccountNumber) {

        // ver que no falte ningun parametro escencial
        if ( ammount.isNaN() ) {
            return new ResponseEntity<>("Missing an ammount to transfer", HttpStatus.FORBIDDEN);
        }

        if ( originAccountNumber.isEmpty() ) {
            return new ResponseEntity<>("Missing the origin account", HttpStatus.FORBIDDEN);
        }

        if ( destinyAccountNumber.isEmpty() ) {
            return new ResponseEntity<>("Missing the destiny account", HttpStatus.FORBIDDEN);
        }

        // ver que no se transfiera a la misma cuenta
        if ( originAccountNumber.equals(destinyAccountNumber) ) {
            return new ResponseEntity<>("You cant transfer to the same account!", HttpStatus.FORBIDDEN);
        }

        // checkear la cuenta de origen
        if ( !accountRepository.existsByNumber(originAccountNumber) ) {
            return new ResponseEntity<>("The origin account does not exist", HttpStatus.FORBIDDEN);
        }

        // agregar limitiacion de longitud a la description
        if (description.length() > 250) {
            return new ResponseEntity<>("The description is too long (can´t be longer than 250 characters)", HttpStatus.FORBIDDEN);
        }

        // tengo que ver que la cuenta y el cliente esten asociados, ver las
        // cuentas del cliente o ver el cliente de la cuenta
        Client client = clientRepository.findByEmail(authentication.getName());
        Account originAccount = accountRepository.findByNumber(originAccountNumber);
        Account destinyAccount = accountRepository.findByNumber(destinyAccountNumber);
        if ( !client.getAccounts().contains(originAccount) ) {
            return new ResponseEntity<>("The origin account does not belong to this client", HttpStatus.FORBIDDEN);
        }

        // checkear la cuenta de destino
        if ( !accountRepository.existsByNumber(destinyAccountNumber) ) {
            return new ResponseEntity<>("The Destiny account does not exist", HttpStatus.FORBIDDEN);
        }

        // checkear que se dispone de monto suficiente
        if (ammount > originAccount.getBalance()) {
            return new ResponseEntity<>("You noy have enough funds for this transaction", HttpStatus.FORBIDDEN);
        }

        String sentDescription = originAccountNumber + " " + description;
        String receivedDescription = "Transaction from " + client.fullName() + ", account number " + destinyAccountNumber + ". " + description;

        // creo las transacciones
        Transaction outgoingTransaction = new Transaction( TransactionType.DEBIT, -ammount, sentDescription, LocalDateTime.now() );
        Transaction incomingTransaction = new Transaction( TransactionType.CREDIT, ammount, receivedDescription, LocalDateTime.now() );

        // cambio los balances de las cuentas
        originAccount.setBalance(originAccount.getBalance() - ammount);
        destinyAccount.setBalance(destinyAccount.getBalance() + ammount);

        // agrego las trasnacciones
        originAccount.addTransaction(outgoingTransaction);
        destinyAccount.addTransaction(incomingTransaction);

        // guardo en la BD las nuevas transacciones y los cambios en las cuentas
        accountRepository.save(originAccount);
        accountRepository.save(destinyAccount);
        transactionRepository.save(outgoingTransaction);
        transactionRepository.save(incomingTransaction);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
