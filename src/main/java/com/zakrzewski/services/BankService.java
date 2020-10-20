package com.zakrzewski.services;

import com.zakrzewski.entity.Client;
import com.zakrzewski.repositories.ClientRepository;

import java.util.Objects;

public class BankService {

    private ClientRepository clientRepository;

    public BankService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public void saveClient(Client client){
        clientRepository.saveClient(client);
    }

    public Client findClientByEmailAddress(String emailAddress){
        return clientRepository.findClientByEmail(emailAddress);
    }

    public void transferAmount(String fromEmail, String toEmail, double amount){
        validateAmount(amount);
        if (fromEmail.equals(toEmail)){
            throw new IllegalArgumentException("fromEmail and toEmail can't be equal! ");
        }
        Client fromClient = clientRepository.findClientByEmail(fromEmail);
        Client toClient = clientRepository.findClientByEmail(toEmail);
        if (fromClient.getBalance() - amount >= 0){
            fromClient.setBalance(fromClient.getBalance() - amount);
            toClient.setBalance(toClient.getBalance() + amount);
        }else {
            throw new NotEnoughFundsException("Not enough funds");
        }

        clientRepository.saveClient(fromClient);
        clientRepository.saveClient(toClient);

    }


    public void withdrawAmount(String emailAddress, double amount) {
        validateAmount(amount);
        if (Objects.isNull(emailAddress)){
            throw new IllegalArgumentException("Email can not be null!");
        }
        String lowerCaseEmail = emailAddress.toLowerCase();
        Client clientByEmail = clientRepository.findClientByEmail(lowerCaseEmail);
        if (amount > clientByEmail.getBalance()){
            throw new NotEnoughFundsException("Not enough funds. Balance must be higher or equal then amount!");
        }
        double newBalance = clientByEmail.getBalance() - amount;
        clientByEmail.setBalance(newBalance);
        clientRepository.saveClient(clientByEmail);
    }

    private void validateAmount(double amount) {
        if (amount <= 0){
            throw new IllegalArgumentException("Negative amount is not allowed!");
        }
    }
}
