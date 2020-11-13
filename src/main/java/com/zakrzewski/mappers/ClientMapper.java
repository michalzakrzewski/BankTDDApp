package com.zakrzewski.mappers;

import com.zakrzewski.dto.ClientRequest;
import com.zakrzewski.dto.ClientResponse;
import com.zakrzewski.entity.Account;
import com.zakrzewski.entity.Client;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ClientMapper {

    public ClientResponse mapToClientResponse(Client client){
        ClientResponse clientResponse = new ClientResponse();
        clientResponse.setId(client.getId());
        clientResponse.setName(client.getName());
        clientResponse.setEmail(client.getEmail());
        clientResponse.setAccounts(client.getAccounts().stream().map(Account::getId).collect(Collectors.toList()));
        return clientResponse;
    }

    public Client mapToClient(ClientRequest clientRequest){
        Client client = new Client();
        client.setName(clientRequest.getName());
        client.setEmail(clientRequest.getEmail());
        return client;
    }
}
