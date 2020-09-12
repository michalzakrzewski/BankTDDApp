package com.zakrzewski;


import com.zakrzewski.models.ClientModel;
import com.zakrzewski.repositories.InMemoryClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BankServiceTest {
    private Set<ClientModel> clients;
    private InMemoryClientRepository inMemoryClientRepository;

    @BeforeEach
    public void setup(){
        clients = new HashSet<>();
        inMemoryClientRepository = new InMemoryClientRepository(clients);
    }

    @Test
    public void verifyIfUserIsAddingCorrectlyToTheRepository(){
        //given
        ClientModel client = new ClientModel("Michał", "Zakrzewski", 26, 5000, "michal@gmail.com");
        ClientModel expectedClient = new ClientModel("Michał", "Zakrzewski", 26, 5000, "michal@gmail.com");
        //when
        inMemoryClientRepository.addClient(client);
        ClientModel actualClient = clients.stream()
                .findFirst()
                .get();
        //then
        assertEquals(expectedClient, actualClient);
    }
}
