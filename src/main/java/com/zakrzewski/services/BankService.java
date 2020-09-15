package com.zakrzewski.services;

import com.zakrzewski.models.ClientModel;
import com.zakrzewski.repositories.InMemoryClientRepository;

import java.util.Set;

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
        if (amount < 0){
            throw new IllegalArgumentException("Negative amount is not allowed!");
        }
        if (fromEmail.equals(toEmail)){
            throw new IllegalArgumentException("fromEmail and toEmail can't be equal! ");
        }
        ClientModel fromClient = inMemoryBankRepository.findClientByEmail(fromEmail);
        ClientModel toClient = inMemoryBankRepository.findClientByEmail(toEmail);
        if (fromClient.getAmount() - amount >= 0){
            fromClient.setAmount(fromClient.getAmount() - amount);
            toClient.setAmount(toClient.getAmount() + amount);
        }else {
            throw new IllegalArgumentException("Not enough funds");
        }

    }


}
