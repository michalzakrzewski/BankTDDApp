package com.zakrzewski.services;

import com.zakrzewski.models.ClientModel;
import com.zakrzewski.repositories.InMemoryClientRepository;

import java.util.Objects;

public class BankService {

    private InMemoryClientRepository inMemoryBankRepository;

    public BankService(InMemoryClientRepository inMemoryBankRepository) {
        this.inMemoryBankRepository = inMemoryBankRepository;
    }

    public void saveClient(ClientModel client){
        inMemoryBankRepository.addClient(client);
    }

    public void findAllClients(){
        System.out.println(inMemoryBankRepository.findAllClients());
    }

    public ClientModel findClientByEmailAddress(String emailAddress){
        return inMemoryBankRepository.findClientByEmail(emailAddress);
    }

    public void transferAmount(String fromEmail, String toEmail, double amount){
        validateAmount(amount);
        if (fromEmail.equals(toEmail)){
            throw new IllegalArgumentException("fromEmail and toEmail can't be equal! ");
        }
        ClientModel fromClient = inMemoryBankRepository.findClientByEmail(fromEmail);
        ClientModel toClient = inMemoryBankRepository.findClientByEmail(toEmail);
        if (fromClient.getBalance() - amount >= 0){
            fromClient.setBalance(fromClient.getBalance() - amount);
            toClient.setBalance(toClient.getBalance() + amount);
        }else {
            throw new NotEnoughFundsException("Not enough funds");
        }

    }


    public void withdrawAmount(String emailAddress, double amount) {
        validateAmount(amount);
        if (Objects.isNull(emailAddress)){
            throw new IllegalArgumentException("Email can not be null!");
        }
        String lowerCaseEmail = emailAddress.toLowerCase();
        ClientModel clientByEmail = inMemoryBankRepository.findClientByEmail(lowerCaseEmail);
        if (amount > clientByEmail.getBalance()){
            throw new NotEnoughFundsException("Not enough funds. Balance must be higher or equal then amount!");
        }
        double newBalance = clientByEmail.getBalance() - amount;
        clientByEmail.setBalance(newBalance);
    }

    private void validateAmount(double amount) {
        if (amount <= 0){
            throw new IllegalArgumentException("Negative amount is not allowed!");
        }
    }
}
