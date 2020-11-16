package com.zakrzewski.dto;

public class AccountResponse {

    private long id;
    private double balance;
    private String currency;
    private Long userId;

    public AccountResponse() {
    }

    public AccountResponse(long id, double balance, String currency, Long userId) {
        this.id = id;
        this.balance = balance;
        this.currency = currency;
        this.userId = userId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
