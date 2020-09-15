package com.zakrzewski;


import com.zakrzewski.models.ClientModel;
import com.zakrzewski.repositories.InMemoryClientRepository;
import com.zakrzewski.services.BankService;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

//Zasady TDD
/*
1. Nie możesz napisać żadnego produkcyjnego kodu, czyli kodu w aplikacji, dopóki nie napiszesz testu, który zrobi fail.
2. Nie możesz napisać kolejnych testów dopóki test robi fail.
3. Nie możesz pisac kodu produkcyjnego więcej niż potrzeba do tego, aby test, który nam robi fail przeszedł.
 */

public class BankServiceTest {
    private Set<ClientModel> clients;
    private InMemoryClientRepository inMemoryClientRepository;
    private BankService bankService;

    @BeforeEach
    public void setup(){
        clients = new HashSet<>();
        inMemoryClientRepository = new InMemoryClientRepository(clients);
        bankService = new BankService(inMemoryClientRepository);
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

    @Test
    public void transferAmount_allParamsOk_fundsTransferred() {
        //given
        ClientModel clientFrom = new ClientModel("Michał", "Zakrzewski", 26, 50000, "michal@gmail.com");
        ClientModel clientTo = new ClientModel("Client", "Client123", 33, 10000, "client@gmail.com");
        final double amountToTransfer = 10000;
        clients.add(clientFrom);
        clients.add(clientTo);
        //when
        bankService.transferAmount(clientFrom.getEmailAddress(), clientTo.getEmailAddress(), amountToTransfer);
        //then
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions
                .assertThat(clientFrom.getAmount())
                .isEqualTo(40000);
        softAssertions
                .assertThat(clientTo.getAmount())
                .isEqualTo(20000);
        softAssertions.assertAll();
/*        assertEquals(CLIENT_FROM.getAmount(), 40000);
        assertEquals(CLIENT_TO.getAmount(), 20000);*/
    }

    @Test
    public void transfer_allFunds_fundsTransferred(){
        //given
        ClientModel clientFrom = new ClientModel("Michał", "Zakrzewski", 26, 50000, "michal@gmail.com");
        ClientModel clientTo = new ClientModel("Client", "Client123", 33, 10000, "client@gmail.com");
        final double amountToTransfer = 50000;
        clients.add(clientFrom);
        clients.add(clientTo);
        //when
        bankService.transferAmount(clientFrom.getEmailAddress(), clientTo.getEmailAddress(), amountToTransfer);
        //then
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions
                .assertThat(clientFrom.getAmount())
                .isEqualTo(0);
        softAssertions
                .assertThat(clientTo.getAmount())
                .isEqualTo(60000);
        softAssertions.assertAll();
    }
}
