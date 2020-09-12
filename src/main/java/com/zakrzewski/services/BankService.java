package com.zakrzewski.services;

import com.sun.security.ntlm.Client;
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

    public Set<ClientModel> findAllClients(){
        return inMemoryBankRepository.findAllClients();
    }

    public ClientModel findClientByEmailAddress(String emailAddress){
        return inMemoryBankRepository.findClientByEmail(emailAddress);
    }


}
