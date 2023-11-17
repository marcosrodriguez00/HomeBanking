package com.mindhub.homebanking.dto;

public class CardPaymentDTO {

    private String cardNumber, paymentDescription;

    private double paymentAmount;

    public CardPaymentDTO(String cardNumber, String paymentDescription, double paymentAmount) {
        this.cardNumber = cardNumber;
        this.paymentDescription = paymentDescription;
        this.paymentAmount = paymentAmount;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getPaymentDescription() {
        return paymentDescription;
    }

    public double getPaymentAmount() {
        return paymentAmount;
    }
}
