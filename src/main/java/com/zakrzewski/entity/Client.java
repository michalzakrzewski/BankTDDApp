package com.zakrzewski.entity;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "USERS")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long id;

    @Column(name = "FIRST_NAME")
    private String name;

    @Column(name = "MAIL")
    private String emailAddress;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID")
    private List<Account> accounts;

    public Client() {
    }

    public Client(String name, String emailAddress, List<Account> accounts) {
        this.name = name;
        this.emailAddress = emailAddress;
        this.accounts = accounts;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(id, client.id) &&
                Objects.equals(name, client.name) &&
                Objects.equals(emailAddress, client.emailAddress) &&
                Objects.equals(accounts, client.accounts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, emailAddress, accounts);
    }


    public double getBalance() {
        if (!accounts.isEmpty()){
            return accounts.get(0).getBalance();
        }
        return 0;
    }

    public void setBalance(double newBalance) {
        if (!accounts.isEmpty()){
            accounts.get(0).setBalance(newBalance);
        }
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", accounts=" + accounts +
                '}';
    }
}
