package com.mindhub.homebanking.dto;

import com.mindhub.homebanking.models.Loan;

import java.util.List;

public class LoanDTO {
    private Long id;
    private String name;
    private Double maxAmount, interestRate;
    private List<Integer> payments;

    private boolean isActive;

    public LoanDTO(Loan loan) {
        this.id = loan.getId();
        this.name = loan.getName();
        this.maxAmount = loan.getMaxAmount();
        this.payments = loan.getPayments();
        this.interestRate = loan.getInterestRate();
        this.isActive = loan.isActive();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getMaxAmount() {
        return maxAmount;
    }

    public List<Integer> getPayments() {
        return payments;
    }

    public Double getInterestRate() {
        return interestRate;
    }

    public boolean isActive() {
        return isActive;
    }
}