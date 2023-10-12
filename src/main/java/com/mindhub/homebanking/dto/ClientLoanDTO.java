package com.mindhub.homebanking.dto;

import com.mindhub.homebanking.models.ClientLoan;

public class ClientLoanDTO {

    private Long ID, loanID;

    private String name;

    private int payments;

    private double amount;

    public ClientLoanDTO(ClientLoan clientLoan) {
        this.ID = clientLoan.getID();
        this.name = clientLoan.getLoan().getName();
        this.loanID = clientLoan.getLoan().getID();
        this.amount = clientLoan.getAmount();
        this.payments = clientLoan.getPayments();
    }

    public Long getID() {
        return ID;
    }

    public Long getLoanID() {
        return loanID;
    }

    public String getName() {
        return name;
    }

    public double getAmount() {
        return amount;
    }

    public int getPayments() {
        return payments;
    }
}
