package com.mindhub.homebanking.dto;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

public class AccountDTO {

    private long id;

/*    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="client_id")
    private Client client;*/

    private String number;

    private double balance;

    private LocalDate creationDate;

    public AccountDTO(Account account) {
        number = account.getNumber();
        balance = account.getBalance();
        creationDate = account.getCreationDate();
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
