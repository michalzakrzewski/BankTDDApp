package com.zakrzewski.services;

import com.zakrzewski.dto.ClientRequest;
import com.zakrzewski.dto.ClientResponse;
import com.zakrzewski.entity.Client;
import com.zakrzewski.mappers.ClientMapper;
import com.zakrzewski.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    private ClientRepository clientRepository;
    private ClientMapper clientMapper;

    @Autowired
    public ClientService(ClientRepository clientRepository, ClientMapper clientMapper) {
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
}
