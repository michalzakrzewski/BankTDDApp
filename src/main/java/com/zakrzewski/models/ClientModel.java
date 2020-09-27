package com.zakrzewski.models;

import java.util.Objects;

public class ClientModel {

    private String name;
    private String emailAddress;
    private double balance;

    public ClientModel(String name, String emailAddress, double balance) {
        this.name = name;
        this.emailAddress = emailAddress;
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientModel that = (ClientModel) o;
        return Double.compare(that.balance, balance) == 0 &&
                Objects.equals(name, that.name) &&
                Objects.equals(emailAddress, that.emailAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, emailAddress, balance);
    }

    @Override
    public String toString() {
        return "ClientModel{" +
                "name='" + name + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", balance=" + balance +
                '}';
    }
}
