package com.mindhub.homebanking.dto;

import com.mindhub.homebanking.models.ClientLoan;

public class ClientLoanDTO {

    private Long id, loanId;

    private String name;

    private int payments, payedPayments;

    private double amount, payedAmount, eachPaymentAmount;

    private boolean isActive;

    public ClientLoanDTO(ClientLoan clientLoan) {
        this.id = clientLoan.getId();
        this.name = clientLoan.getLoan().getName();
        this.loanId = clientLoan.getLoan().getId();
        this.amount = clientLoan.getAmount();
        this.payments = clientLoan.getPayments();
        this.payedAmount = clientLoan.getPayedAmount();
        this.payedPayments = clientLoan.getPayedPayments();
        this.eachPaymentAmount = clientLoan.getEachPaymentAmount();
        this.isActive = clientLoan.isActive();
    }

    public Long getID() {
        return id;
    }

    public Long getLoanId() {
        return loanId;
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

    public int getPayedPayments() {
        return payedPayments;
    }

    public double getPayedAmount() {
        return payedAmount;
    }

    public double getEachPaymentAmount() {
        return eachPaymentAmount;
    }

    public boolean isActive() {
        return isActive;
    }
}
