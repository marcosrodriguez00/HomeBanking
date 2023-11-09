package com.mindhub.homebanking.Controllers;

import com.mindhub.homebanking.dto.TransactionDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static com.mindhub.homebanking.utils.LoanUtils.dateFormatter;

@RestController
@RequestMapping("/api")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @Autowired
    AccountService accountService;

    @Autowired
    ClientService clientService;

    @GetMapping("/transactions")
    public List<TransactionDTO> getAllTransactions() {
        return transactionService.getAllTransactionDTO();
    }

    @GetMapping("/transactions/{id}")
    public TransactionDTO getTransaction(@PathVariable Long id) {
        return transactionService.getTransactionDTO(id);
    }

    @Transactional
    @PostMapping(path = "/transactions")
    public ResponseEntity<Object> transaction (
            Authentication authentication,
            @RequestParam Double amount, @RequestParam String description,
            @RequestParam String originAccountNumber, @RequestParam String destinyAccountNumber) {

        // ver que no falte ningun parametro escencial
        if ( amount.isNaN() ) {
            return new ResponseEntity<>("Missing an amount to transfer", HttpStatus.FORBIDDEN);
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
        if ( !accountService.existsAccountByNumber(originAccountNumber) ) {
            return new ResponseEntity<>("The origin account does not exist", HttpStatus.FORBIDDEN);
        }

        // agregar limitiacion de longitud a la description
        if (description.length() > 250) {
            return new ResponseEntity<>("The description is too long (can´t be longer than 250 characters)", HttpStatus.FORBIDDEN);
        }

        // tengo que ver que la cuenta y el cliente esten asociados, ver las
        // cuentas del cliente o ver el cliente de la cuenta
        Client client = clientService.getClientByEmail(authentication.getName());
        Account originAccount = accountService.getAccountByNumber(originAccountNumber);
        Account destinyAccount = accountService.getAccountByNumber(destinyAccountNumber);
        if ( !client.getAccounts().contains(originAccount) ) {
            return new ResponseEntity<>("The origin account does not belong to this client", HttpStatus.FORBIDDEN);
        }

        // checkear la cuenta de destino
        if ( !accountService.existsAccountByNumber(destinyAccountNumber) ) {
            return new ResponseEntity<>("The Destiny account does not exist", HttpStatus.FORBIDDEN);
        }

        // checkear que se dispone de monto suficiente
        if (amount > originAccount.getBalance()) {
            return new ResponseEntity<>("You noy have enough funds for this transaction", HttpStatus.FORBIDDEN);
        }

        String sentDescription = originAccountNumber + " " + description;
        String receivedDescription = "Transaction from " + client.fullName() + ", account number " + destinyAccountNumber + ". " + description;

        // creo las transacciones
        Transaction outgoingTransaction = new Transaction( TransactionType.DEBIT, -amount, sentDescription, dateFormatter(LocalDateTime.now()) );
        Transaction incomingTransaction = new Transaction( TransactionType.CREDIT, amount, receivedDescription, dateFormatter(LocalDateTime.now()) );

        // cambio los balances de las cuentas
        originAccount.setBalance(originAccount.getBalance() - amount);
        destinyAccount.setBalance(destinyAccount.getBalance() + amount);

        // agrego las trasnacciones
        originAccount.addTransaction(outgoingTransaction);
        destinyAccount.addTransaction(incomingTransaction);

        // guardo en la BD las nuevas transacciones y los cambios en las cuentas
        accountService.saveAccount(originAccount);
        accountService.saveAccount(destinyAccount);
        transactionService.saveTransaction(outgoingTransaction);
        transactionService.saveTransaction(incomingTransaction);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
