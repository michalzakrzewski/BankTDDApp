package com.zakrzewski.services;

import com.zakrzewski.entity.Client;
import com.zakrzewski.repositories.ClientSpringJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class BankService {

    private ClientSpringJPARepository clientRepository;

    @Autowired
    public BankService(ClientSpringJPARepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public void saveClient(Client client){
        clientRepository.save(client);
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

        clientRepository.save(fromClient);
        clientRepository.save(toClient);

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
        clientRepository.save(clientByEmail);
    }

    private void validateAmount(double amount) {
        if (amount <= 0){
            throw new IllegalArgumentException("Negative amount is not allowed!");
        }
    }
}
