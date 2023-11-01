package com.mindhub.homebanking.Controllers;

import com.mindhub.homebanking.dto.LoanApplicationDTO;
import com.mindhub.homebanking.dto.LoanDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class LoanController {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private ClientLoanRepository clientLoanRepository;

    public LocalDateTime dateFormatter (LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = localDateTime.format(formatter);

        return LocalDateTime.parse(formattedDateTime, formatter);
    }

    @RequestMapping("/loans")
    public List<LoanDTO> getAllLoans () {
        return loanRepository.findAll().stream().map(LoanDTO::new).collect(toList());
    }

    public double addInterest ( double amount ) {
        return amount + (amount * 0.2);
    }

    String approvedMessage = "Loan approved";

    @Transactional
    @PostMapping("/loans")
    public ResponseEntity<Object> requestLoan (@RequestBody LoanApplicationDTO loanApplication, Authentication authentication) {

        Client client = clientRepository.findByEmail(authentication.getName());

        Loan loan = loanRepository.findById(loanApplication.getId()).orElse(null);

        Account account = accountRepository.findByNumber(loanApplication.getDestinyAccountNumber());

        if ( loanApplication.getDestinyAccountNumber().isBlank() ) {
            return new ResponseEntity<>("Missing an account to take the loan", HttpStatus.FORBIDDEN);
        }

        if ( loanApplication.getAmount() == 0 ) {
            return new ResponseEntity<>("Requested amount cannot be 0", HttpStatus.FORBIDDEN);
        }

        if ( loanApplication.getPayments() == 0 ) {
            return new ResponseEntity<>("The amount of payments cannot be 0", HttpStatus.FORBIDDEN);
        }

        if ( loan == null ) {
            return new ResponseEntity<>("The loan you are applying to does not exist", HttpStatus.FORBIDDEN);
        }

        if ( loanApplication.getAmount() > loan.getMaxAmount() ) {
            return new ResponseEntity<>("The amount you asked for exceeds the maximum for this type of loan", HttpStatus.FORBIDDEN);
        }

        if ( !loan.getPayments().contains(loanApplication.getPayments()) ) {
            return new ResponseEntity<>("Please select an available amount of payments", HttpStatus.FORBIDDEN);
        }

        if ( !accountRepository.existsByNumber(loanApplication.getDestinyAccountNumber()) ){
            return new ResponseEntity<>("The account number you selected does not exist", HttpStatus.FORBIDDEN);
        }

        if ( !client.getAccounts().contains(accountRepository.findByNumber(loanApplication.getDestinyAccountNumber())) ) {
            return new ResponseEntity<>("The account you inserted does not belong to you!", HttpStatus.FORBIDDEN);
        }

        ClientLoan clientLoan = new ClientLoan(addInterest(loanApplication.getAmount()), loanApplication.getPayments());
        client.addClientLoan(clientLoan);
        loan.addClientLoan(clientLoan);

        clientLoanRepository.save(clientLoan);

        Transaction transaction = new Transaction( TransactionType.CREDIT, loanApplication.getAmount(), approvedMessage, dateFormatter(LocalDateTime.now()) );
        account.addTransaction( transaction );
        transactionRepository.save( transaction );
        account.setBalance( account.getBalance() + loanApplication.getAmount() );
        accountRepository.save( account );

        return new ResponseEntity<>("Loan created succesfully",HttpStatus.CREATED);
    }
}
