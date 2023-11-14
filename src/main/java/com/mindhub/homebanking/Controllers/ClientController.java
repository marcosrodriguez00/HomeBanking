package com.mindhub.homebanking.Controllers;

import com.mindhub.homebanking.dto.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.AccountType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static com.mindhub.homebanking.utils.AccountUtils.randomAccountNumber;
import static com.mindhub.homebanking.utils.ClientUtils.isValidEmail;

@RestController
@RequestMapping("/api")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/clients")
    public List<ClientDTO> getAllClientsDTO() {
        return clientService.getAllClientDTO();
    }

    @GetMapping("/clients/{id}")
    public ClientDTO getClient(@PathVariable Long id) {
        return clientService.getClientDTOById(id);
    }

    // ResponseEntity es una clase de spring que da respuestas a traves del controlador
    @PostMapping(path = "/clients")
    public ResponseEntity<Object> register (
            @RequestParam String firstName, @RequestParam String lastName,
            @RequestParam String email, @RequestParam String password) {

        if (firstName.isBlank()) {
            return new ResponseEntity<>("Missing first name", HttpStatus.FORBIDDEN);
        }

        if (lastName.isBlank()) {
            return new ResponseEntity<>("Missing last name", HttpStatus.FORBIDDEN);
        }

        if (email.isBlank()) {
            return new ResponseEntity<>("Missing email", HttpStatus.FORBIDDEN);
        }

        if (!isValidEmail(email)) {
            return new ResponseEntity<>("Email has an invalid format", HttpStatus.FORBIDDEN);
        }

        if (password.isBlank()) {
            return new ResponseEntity<>("Missing password", HttpStatus.FORBIDDEN);
        }

        // usar existsByEmail
        if (clientService.getClientByEmail(email) !=  null) {
            return new ResponseEntity<>("email already in use", HttpStatus.FORBIDDEN);
        }

        // genero un numero de cuenta random
        String accountNumber = randomAccountNumber();

        // me aseguro que no exista el numero de cuenta
        while (accountService.existsAccountByNumber(accountNumber)){
            accountNumber = randomAccountNumber();
        }

        Client client = new Client(firstName, lastName, email, passwordEncoder.encode(password), false);
        clientService.saveClient(client);
        Account account = new Account(accountNumber, 0, LocalDate.now(), AccountType.SAVINGS);
        client.addAccount(account);
        accountService.saveAccount(account);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/clients/currents")
    public ClientDTO getClientCurrent(Authentication authentication){
        return new ClientDTO(clientService.getClientByEmail(authentication.getName()));
    }
}
