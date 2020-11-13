package com.zakrzewski.services;

import com.zakrzewski.dto.ClientRequest;
import com.zakrzewski.dto.ClientResponse;
import com.zakrzewski.entity.Client;
import com.zakrzewski.mappers.ClientMapper;
import com.zakrzewski.repositories.ClientSpringJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ClientService {

    private ClientSpringJPARepository clientRepository;
    private ClientMapper clientMapper;

    @Autowired
    public ClientService(ClientSpringJPARepository clientRepository, ClientMapper clientMapper) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
    }

    public void saveClient(ClientRequest clientRequest){
        Client client = clientMapper.mapToClient(clientRequest);
        clientRepository.save(client);
    }

    public Client findClientByEmailAddress(String emailAddress){
        return clientRepository.findClientByEmail(emailAddress);
    }

    public ClientResponse findResponseByEmail(String email){
        Client clientByEmail = clientRepository.findClientByEmail(email);
        return clientMapper.mapToClientResponse(clientByEmail);
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
