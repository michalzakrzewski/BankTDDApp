package com.zakrzewski.dto;


public class AccountRequest {

    private double balance;
    private String currency;
    private Long userId;

    public AccountRequest() {
    }

    public AccountRequest(double balance, String currency, Long userId) {
        this.balance = balance;
        this.currency = currency;
        this.userId = userId;
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
