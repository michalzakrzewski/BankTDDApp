package com.zakrzewski.models;

import java.util.Objects;

public class ClientModel {

    private String firstName;
    private String lastName;
    private int age;
    private double balance;
    private String emailAddress;

    public ClientModel(String firstName, String lastName, int age, double balance, String emailAddress) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.balance = balance;
        this.emailAddress = emailAddress;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientModel that = (ClientModel) o;
        return age == that.age &&
                Double.compare(that.balance, balance) == 0 &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(emailAddress, that.emailAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, age, balance, emailAddress);
    }

    @Override
    public String toString() {
        return "ClientModel{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", amount=" + balance +
                ", emailAddress='" + emailAddress + '\'' +
                '}';
    }
}
