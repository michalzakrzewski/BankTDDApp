package com.zakrzewski.entity;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.Objects;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TRANSACTION_ID")
    private long id;

    @Column(name = "AMOUNT")
    private double amount;

    @Column(name = "CURRENCY")
    private String currency;

    @Column(name = "TRANSACTION_DATE")
    private OffsetDateTime transactionDate;

    @Column(name = "FROM_ACCOUNT_ID")
    private long fromAccountId;

    @Column(name = "TO_ACCOUNT_ID")
    private long toAccountId;

    public Transaction() {
    }

    public Transaction(long id, double amount, String currency, OffsetDateTime transactionDate, long fromAccountId, long toAccountId) {
        this.id = id;
        this.amount = amount;
        this.currency = currency;
        this.transactionDate = transactionDate;
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public OffsetDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(OffsetDateTime transactionDate) {
        this.transactionDate = transactionDate;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return id == that.id &&
                Double.compare(that.amount, amount) == 0 &&
                fromAccountId == that.fromAccountId &&
                toAccountId == that.toAccountId &&
                Objects.equals(currency, that.currency) &&
                Objects.equals(transactionDate, that.transactionDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount, currency, transactionDate, fromAccountId, toAccountId);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                ", transactionDate=" + transactionDate +
                ", fromAccountId=" + fromAccountId +
                ", toAccountId=" + toAccountId +
                '}';
    }
}
