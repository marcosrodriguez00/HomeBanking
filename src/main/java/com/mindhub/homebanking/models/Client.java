package com.mindhub.homebanking.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long ID;

    @OneToMany(mappedBy="client", fetch= FetchType.EAGER)
    private Set<Account> accounts = new HashSet<>();

    private String firstName, lastName, email;

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    Set<ClientLoan> clientLoans;

    public Client() {}

    public Client(String firstName, String lastName, String email){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public long getID() {
        return ID;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    public void addAccount(Account account) {
        account.setClient(this);
        this.accounts.add(account);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Client{" +
                "ID=" + ID +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    @JsonIgnore
    public List<Loan> getLoans() {
        List<Loan> loans = new ArrayList<>();
        for (ClientLoan clientLoan : clientLoans) {
            loans.add(clientLoan.getLoan());
        }
        return loans;
    }

    public void addLoan(ClientLoan clientLoan) {
        clientLoan.setClient(this);
        clientLoans.add(clientLoan);
    }
}
