package com.mindhub.homebanking.dto;

public class LoanApplicationDTO {

    private long loanId;

    private int payments;

    private double amount;

    private String destinyAccountNumber;

    public LoanApplicationDTO(long loanId, int payments, double amount, String destinyAccountNumber) {
        this.loanId = loanId;
        this.payments = payments;
        this.amount = amount;
        this.destinyAccountNumber = destinyAccountNumber;
    }

    public long getId() {
        return loanId;
    }

    public int getPayments() {
        return payments;
    }

    public double getAmount() {
        return amount;
    }

    public String getDestinyAccountNumber() {
        return destinyAccountNumber;
    }
}
