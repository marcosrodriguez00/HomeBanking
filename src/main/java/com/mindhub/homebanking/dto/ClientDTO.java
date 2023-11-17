package com.mindhub.homebanking.dto;

import com.mindhub.homebanking.models.*;

import java.util.Set;
import java.util.stream.Collectors;

public class ClientDTO {

    private long id;

    private Set<AccountDTO> accounts;

    private String firstName, lastName, email;

    private boolean isAdmin;

    private Set<ClientLoanDTO> loans;

    private Set<CardDTO> cards;

    public ClientDTO(Client client) {
        this.id = client.getId();
        this.firstName = client.getFirstName();
        this.lastName = client.getLastName();
        this.email = client.getEmail();
        this.isAdmin = client.isAdmin();
        this.accounts = client.getAccounts().stream().filter(Account::isActive).map(AccountDTO::new).collect(Collectors.toSet());
        this.loans = client.getClientLoans().stream().filter(ClientLoan::isActive).map(ClientLoanDTO::new).collect(Collectors.toSet());
        this.cards = client.getCards().stream().filter(Card::isActive).map(CardDTO::new).collect(Collectors.toSet());
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public Set<AccountDTO> getAccounts() {
        return accounts;
    }

    public Set<ClientLoanDTO> getLoans() { return loans; }

    public Set<CardDTO> getCards() { return cards; }

    public boolean isAdmin() {
        return isAdmin;
    }
}
