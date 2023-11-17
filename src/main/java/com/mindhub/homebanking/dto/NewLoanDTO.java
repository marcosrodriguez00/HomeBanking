package com.mindhub.homebanking.dto;

import java.util.List;

public class NewLoanDTO {

    private String name;

    private double maxAmount, interestRate;

    private List<Integer> payments;

    public NewLoanDTO(String name, double maxAmount, double interestRate, List<Integer> payments) {
        this.name = name;
        this.maxAmount = maxAmount;
        this.interestRate = interestRate;
        this.payments = payments;
    }

    public String getName() {
        return name;
    }

    public double getMaxAmount() {
        return maxAmount;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public List<Integer> getPayments() {
        return payments;
    }
}
