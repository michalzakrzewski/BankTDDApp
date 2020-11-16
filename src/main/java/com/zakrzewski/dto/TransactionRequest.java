package com.zakrzewski.dto;

public class TransactionRequest {

    private double amount;

    private String currency;

    private long fromAccountId;

    private long toAccountId;

    public TransactionRequest() {
    }

    public TransactionRequest(double amount, String currency, long fromAccountId, long toAccountId) {
        this.amount = amount;
        this.currency = currency;
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public long getFromAccountId() {
        return fromAccountId;
    }

    public void setFromAccountId(long fromAccountId) {
        this.fromAccountId = fromAccountId;
    }

    public long getToAccountId() {
        return toAccountId;
    }

    public void setToAccountId(long toAccountId) {
        this.toAccountId = toAccountId;
    }
}
