package com.mindhub.homebanking.Controllers;

import com.mindhub.homebanking.dto.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping("/clients")
    public List<ClientDTO> getAllClientsDTO() {
        return clientService.getAllClientDTO();
    }

    @RequestMapping("/clients/{id}")
    public ClientDTO getClient(@PathVariable Long id) {
        return clientService.getClientDTOById(id);
    }

    public String randomAccountNumber() {
        int randomNumber = (int) (Math.random() * (99999999));
        return "VIN-" + randomNumber;
    }

    public static boolean isValidEmail(String email) {
        // Define la expresión regular para validar el formato de un correo electrónico
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";

        // Compila la expresión regular en un patrón
        Pattern pattern = Pattern.compile(emailRegex);

        // Crea un objeto Matcher para comparar el correo electrónico con el patrón
        Matcher matcher = pattern.matcher(email);

        // Verifica si el correo electrónico coincide con el patrón
        return matcher.matches();
    }

    // ResponseEntity es una clase de spring que da respuestas a traves del controlador
    @RequestMapping(path = "/clients", method = RequestMethod.POST)
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
        Account account = new Account(accountNumber, 0, LocalDate.now());
        client.addAccount(account);
        accountService.saveAccount(account);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping("/clients/currents")
    public ClientDTO getClientCurrent(Authentication authentication){
        return new ClientDTO(clientService.getClientByEmail(authentication.getName()));
    }
}
