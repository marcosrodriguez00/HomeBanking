package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class ClientLoan {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    private double amount, payedAmount, eachPaymentAmount;

    private int payments, payedPayments;

    private boolean isActive;

    @ManyToOne( fetch = FetchType.EAGER )
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne( fetch = FetchType.EAGER )
    @JoinColumn(name = "loan_id")
    private Loan loan;

    public ClientLoan() { }

    public ClientLoan(double amount, int payments) {
        this.amount = amount;
        this.payments = payments;
        this.payedAmount = 0;
        this.payedPayments = 0;
        this.eachPaymentAmount = 0;
        this.isActive = true;
    }

    // Esto no cumple con la especificidad única
    public ClientLoan(double amount, Client client, Loan loan, int payments){
        this.amount = amount;
        this.client = client;
        this.loan = loan;
        this.payments = payments;
        this.payedPayments = 0;
        this.payedAmount = 0;
        this.eachPaymentAmount = 0;
        this.isActive = true;
    }

    public long getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getPayments() {
        return payments;
    }

    public void setPayments(int payments) {
        this.payments = payments;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }

    public double getPayedAmount() {
        return payedAmount;
    }

    public void setPayedAmount(double payedAmount) {
        this.payedAmount = payedAmount;
    }

    public int getPayedPayments() {
        return payedPayments;
    }

    public void setPayedPayments(int payedPayments) {
        this.payedPayments = payedPayments;
    }

    public double getEachPaymentAmount() {
        return eachPaymentAmount;
    }

    public void setEachPaymentAmount(double eachPaymentAmount) {
        this.eachPaymentAmount = eachPaymentAmount;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
