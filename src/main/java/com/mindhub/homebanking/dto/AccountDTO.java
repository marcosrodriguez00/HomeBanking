package com.mindhub.homebanking.dto;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class AccountDTO {

    private long id;

/*    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="client_id")
    private Client client;*/

    private String number;

    private double balance;

    private LocalDate creationDate;

    private List<TransactionDTO> transactions;

    public AccountDTO(Account account) {
        id = account.getId();
        number = account.getNumber();
        balance = account.getBalance();
        creationDate = account.getCreationDate();
        transactions = account.getTransactions().stream().map(TransactionDTO::new).collect(Collectors.toList());
    }

    public long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public double getBalance() {
        return balance;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }
}
