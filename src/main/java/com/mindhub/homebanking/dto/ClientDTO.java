package com.mindhub.homebanking.dto;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Loan;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ClientDTO {

    private long ID;

    private Set<AccountDTO> accounts;
    private String firstName, lastName, email;

    private List<ClientLoanDTO> loans;

    public ClientDTO(Client client) {
        this.ID = client.getID();
        this.firstName = client.getFirstName();
        this.lastName = client.getLastName();
        this.email = client.getEmail();
        this.accounts = client.getAccounts().stream().map(AccountDTO::new).collect(Collectors.toSet());
        // quiero devolver clientLoanDTO, getLoans me da lista de Loan
        this.loans = client.getClientLoans().stream().map(ClientLoanDTO::new).collect(Collectors.toList());
    }

    public long getID() {
        return ID;
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

    public List<ClientLoanDTO> getLoans() { return loans; }
}
