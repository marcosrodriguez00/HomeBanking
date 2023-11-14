package com.mindhub.homebanking.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity // define que una tabla representa una tabla en una base de datos relacional, la marca como una entidad persistente
public class Account {

    @Id //marca como primary key, este tipo de datos no se pueden repetir
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native") // indica como se generara automaticamente la primary key
    @GenericGenerator(name = "native", strategy = "native") // se usa para personalizar la generacion de los valores
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="client_id")
    private Client client;

    @OneToMany(mappedBy="account", fetch= FetchType.EAGER)
    private Set<Transaction> transactions = new HashSet<>();

    private String number;

    private double balance;

    private LocalDate creationDate;

    private boolean isActive;

    private AccountType accountType;

    public Account(){}

    public Account(String number, double balance, LocalDate creationDate, AccountType accountType){
        this.number = number;
        this.creationDate = creationDate;
        this.balance = balance;
        this.isActive = true;
        this.accountType = accountType;
    }

    public long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    @JsonIgnore // Evita que se genere la recursividad en la API
    public Client getClient() {
        return client;
    }

    public void addTransaction(Transaction transaction) {
        transaction.setAccount(this);
        this.transactions.add(transaction);
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }
}
