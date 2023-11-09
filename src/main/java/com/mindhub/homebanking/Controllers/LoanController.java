package com.mindhub.homebanking.Controllers;

import com.mindhub.homebanking.dto.LoanApplicationDTO;
import com.mindhub.homebanking.dto.LoanDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static com.mindhub.homebanking.utils.LoanUtils.*;


@RestController
@RequestMapping("/api")
public class LoanController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private LoanService loanService;

    @Autowired
    private ClientLoanService clientLoanService;

    @GetMapping("/loans")
    public List<LoanDTO> getAllLoans () {
        return loanService.getAllLoanDTO();
    }

    String approvedMessage = "Loan approved";

    @Transactional
    @PostMapping("/loans")
    public ResponseEntity<Object> requestLoan (@RequestBody LoanApplicationDTO loanApplication, Authentication authentication) {

        Client client = clientService.getClientByEmail(authentication.getName());

        Loan loan = loanService.getLoanById(loanApplication.getId());

        Account account = accountService.getAccountByNumber(loanApplication.getDestinyAccountNumber());

        if ( loanApplication.getDestinyAccountNumber().isBlank() ) {
            return new ResponseEntity<>("Missing an account to take the loan", HttpStatus.FORBIDDEN);
        }

        if ( loanApplication.getAmount() <= 0 ) {
            return new ResponseEntity<>("Requested amount cannot be 0 or less", HttpStatus.FORBIDDEN);
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

        if ( !accountService.existsAccountByNumber(loanApplication.getDestinyAccountNumber()) ){
            return new ResponseEntity<>("The account number you selected does not exist", HttpStatus.FORBIDDEN);
        }

        if ( !client.getAccounts().contains(accountService.getAccountByNumber(loanApplication.getDestinyAccountNumber())) ) {
            return new ResponseEntity<>("The account you inserted does not belong to you!", HttpStatus.FORBIDDEN);
        }

        ClientLoan clientLoan = new ClientLoan( addInterest(loanApplication.getAmount()), loanApplication.getPayments() );
        client.addClientLoan( clientLoan );
        loan.addClientLoan( clientLoan );

        clientLoanService.saveClientLoan( clientLoan );

        Transaction transaction = new Transaction( TransactionType.CREDIT, loanApplication.getAmount(), approvedMessage, dateFormatter(LocalDateTime.now()) );
        account.addTransaction( transaction );
        transactionService.saveTransaction( transaction );
        account.setBalance( account.getBalance() + loanApplication.getAmount() );
        accountService.saveAccount( account );

        return new ResponseEntity<>("Loan created successfully",HttpStatus.CREATED);
    }
}
