package com.zakrzewski.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "ACCOUNTS")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ACCOUNT_ID")
    private Long id;

    @Column(name = "BALANCE")
    private double balance;

    @Column(name = "CURRENCY")
    private String currency;

    public Account() {
    }
    public Account(double balance, String currency) {
        this.balance = balance;
        this.currency = currency;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Double.compare(account.balance, balance) == 0 &&
                Objects.equals(id, account.id) &&
                Objects.equals(currency, account.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, balance, currency);
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", balance=" + balance +
                ", currency='" + currency + '\'' +
                '}';
    }
}
