package com.mindhub.homebanking.Controllers;

import com.mindhub.homebanking.dto.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private ClientService clientService;

    @RequestMapping("/accounts")
    public List<AccountDTO> getAllAccounts() {
       return accountService.getAllAccountDTO();
    }

    @RequestMapping("/accounts/{id}")
    public AccountDTO getAccount(@PathVariable Long id) { // PathVariable se usa para que el metodo getAccount tome el valor de {id}
        return accountService.getAccountDTOById(id);
    }

    public String randomAccountNumber() {
       int randomNumber = (int) (Math.random() * (99999999 - 11111111) + 11111111);
       return "VIN-" + randomNumber;
    }

    // currents o current
    @PostMapping("/clients/current/accounts")
    public ResponseEntity<Object> newAccount(Authentication authentication) {

        // encapsulo el cliente
        Client client = clientService.getClientByEmail(authentication.getName());

        // controlo que no haya mas de 3 cuentas
        if (client.getAccounts().size() >= 3) {
            return new ResponseEntity<>("Cannot create any more accounts for this client", HttpStatus.FORBIDDEN);
        }

        // genero un numero de cuenta random
        String accountNumber = randomAccountNumber();

        // me aseguro que no exista el numero de cuenta
        while (accountService.existsAccountByNumber(accountNumber)){
            accountNumber = randomAccountNumber();
        }

        // Creo la cuenta nueva y la agrego al cliente
        Account account = new Account(accountNumber, 0, LocalDate.now());
        client.addAccount(account);

        // guardo el cliente con la nueva cuenta y devuelvo respuesta exitosa
        clientService.saveClient(client);
        accountService.saveAccount(account);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
