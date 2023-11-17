package com.mindhub.homebanking.Controllers;

import com.mindhub.homebanking.dto.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.AccountType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


import static com.mindhub.homebanking.utils.AccountUtils.randomAccountNumber;

//se utiliza para marcar una clase como un controlador de Spring MVC que maneja solicitudes web y devuelve datos
@RestController
@RequestMapping("/api")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/accounts")
    public List<AccountDTO> getAllAccounts() {
       return accountService.getAllAccountDTO();
    }

    @GetMapping("/accounts/{id}")
    public AccountDTO getAccount(@PathVariable Long id) { // PathVariable se usa para que el metodo getAccount tome el valor de {id}
        return accountService.getAccountDTOById(id);
    }

    @PostMapping("/clients/current/accounts")
    public ResponseEntity<Object> newAccount(Authentication authentication, @RequestParam AccountType accountType) {

        // defino el cliente
        Client client = clientService.getClientByEmail(authentication.getName());

        // controlo que no haya mas de 3 cuentas
        if (client.getAccounts().stream().filter(Account::isActive).count() >= 3) {
            return new ResponseEntity<>("Cannot create any more accounts for this client", HttpStatus.FORBIDDEN);
        }

        // genero un numero de cuenta random
        String accountNumber = randomAccountNumber();

        // me aseguro que no exista el numero de cuenta
        while (accountService.existsAccountByNumber(accountNumber)){
            accountNumber = randomAccountNumber();
        }

        // Creo la cuenta nueva y la agrego al cliente
        Account account = new Account(accountNumber, 0, LocalDate.now(), accountType);
        client.addAccount(account);

        // guardo el cliente con la nueva cuenta y devuelvo respuesta exitosa
        clientService.saveClient(client);
        accountService.saveAccount(account);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/clients/current/accounts/delete")
    public ResponseEntity<Object> deactivateAccount(Authentication authentication,
                                                    @RequestParam String accountNumber) {

        // verificar que exista la cuenta
        if( !accountService.existsAccountByNumber(accountNumber) ) {
            return new ResponseEntity<>("This account doesn't exist", HttpStatus.FORBIDDEN);
        }

        // verificar que le pertenezca al cliente
        Client client = clientService.getClientByEmail(authentication.getName());
        Account account = accountService.getAccountByNumber(accountNumber);
        if ( !client.getAccounts().contains(account) ) {
            return new ResponseEntity<>("This account doesn't belong to you", HttpStatus.FORBIDDEN);
        }

        // verificar que no tenga plata
        if ( account.getBalance() >= 1 ) {
            return new ResponseEntity<>("You can't delete an account with money in it, please transfer the money first",
                    HttpStatus.FORBIDDEN);
        }

        // verificar que no se quede sin cuentas activas
        if (client.getAccounts().size() == 1) {
            return new ResponseEntity<>("You need to have at least one active account", HttpStatus.FORBIDDEN);
        }

        accountService.deleteAccountByNumber(accountNumber);
        transactionService.deleteTransactions(accountNumber);
        return new ResponseEntity<>("Account deactivated", HttpStatus.CREATED);
    }

}
