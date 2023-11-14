package com.mindhub.homebanking.dto;

import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;

import java.time.LocalDateTime;

public class TransactionDTO {

    private long id;

    private TransactionType type;

    private double amount, currentBalance;

    private LocalDateTime date;

    private String description;

    private boolean isActive;

    public TransactionDTO(Transaction transaction) {
        this.id = transaction.getId();
        this.type = transaction.getType();
        this.amount = transaction.getAmount();
        this.description = transaction.getDescription();
        this.date = transaction.getDate();
        this.currentBalance = transaction.getCurrentBalance();
        this.isActive = transaction.isActive();
    }

    public long getId() {
        return id;
    }

    public TransactionType getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public double getCurrentBalance() {
        return currentBalance;
    }

    public boolean isActive() {
        return isActive;
    }
}