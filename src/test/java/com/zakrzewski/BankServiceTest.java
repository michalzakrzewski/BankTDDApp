package com.zakrzewski;


import com.zakrzewski.models.ClientModel;
import com.zakrzewski.repositories.InMemoryClientRepository;
import com.zakrzewski.services.BankService;
import com.zakrzewski.services.NotEnoughFundsException;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BankServiceTest {
    private List<ClientModel> clients;
    private InMemoryClientRepository inMemoryClientRepository;
    private BankService bankService;

    @BeforeEach
    public void setup(){
        clients = new ArrayList<>();
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
/*        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions
                .assertThat(clientFrom.getAmount())
                .isEqualTo(40000);
        softAssertions
                .assertThat(clientTo.getAmount())
                .isEqualTo(20000);
        softAssertions.assertAll();*/
        assertEquals(40000, clientFrom.getBalance());
        assertEquals(20000, clientTo.getBalance() );
    }

    @Test
    public void transfer_sendAllFunds_fundsTransferred(){
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
                .assertThat(clientFrom.getBalance())
                .isEqualTo(0);
        softAssertions
                .assertThat(clientTo.getBalance())
                .isEqualTo(60000);
        softAssertions.assertAll();
    }

    @Test
    public void transfer_notEnoughFunds_throwNotEnoughFundException(){
        //given
        ClientModel clientFrom = new ClientModel("Michał", "Zakrzewski", 26, 50000, "michal@gmail.com");
        ClientModel clientTo = new ClientModel("Client", "Client123", 33, 10000, "client@gmail.com");
        final double amountToTransfer = 60000;
        clients.add(clientFrom);
        clients.add(clientTo);
        // when/then
        assertThrows(NotEnoughFundsException.class, () -> bankService.transferAmount(clientFrom.getEmailAddress(), clientTo.getEmailAddress(), amountToTransfer));
    }

    @Test
    public void transfer_negativeAmount_throwIllegalArgumentException(){
        //given
        ClientModel clientFrom = new ClientModel("Michał", "Zakrzewski", 26, 50000, "michal@gmail.com");
        ClientModel clientTo = new ClientModel("Client", "Client123", 33, 10000, "client@gmail.com");
        final double amountToTransfer = -5000;
        clients.add(clientFrom);
        clients.add(clientTo);
        // when/then
        assertThrows(IllegalArgumentException.class, () -> bankService.transferAmount(clientFrom.getEmailAddress(), clientTo.getEmailAddress(), amountToTransfer));
    }

    @Test
    public void transfer_toSameClient_thrownIllegalArgumentException(){
        //given
        ClientModel clientFrom = new ClientModel("Michał", "Zakrzewski", 26, 50000, "michal@gmail.com");
        clients.add(clientFrom);
        final double amountToTransfer = 2000;
        // when/then
        assertThrows(IllegalArgumentException.class, () -> bankService.transferAmount(clientFrom.getEmailAddress(), clientFrom.getEmailAddress(), amountToTransfer));
    }

    @Test
    public void withdrawAmount_correctAmount_balanceChangedCorrectly(){
        //given
        ClientModel client = new ClientModel("Michał", "Zakrzewski", 26, 50000, "michal@gmail.com");
        clients.add(client);
        //when
        bankService.withdrawAmount(client.getEmailAddress(), 40000);
        //then
        assertEquals(10000, client.getBalance());
    }

    @Test
    public void withdrawAmount_allBalance_balanceSetToZero(){
        //given
        ClientModel client = new ClientModel("Michał", "Zakrzewski", 26, 50000, "michal@gmail.com");
        clients.add(client);
        //when
        bankService.withdrawAmount(client.getEmailAddress(), 50000);
        //then
        assertEquals(0, client.getBalance());
    }

    @Test
    public void withdrawAmount_negativeAmount_throwIllegalArgumentException(){
        //given
        ClientModel client = new ClientModel("Michał", "Zakrzewski", 26, 50000, "michal@gmail.com");
        clients.add(client);
        int amount = -10000;
        // when/then
        Assertions.assertThrows(IllegalArgumentException.class, () -> bankService.withdrawAmount(client.getEmailAddress(), amount));
    }

    @Test
    public void withdrawAmount_zeroAmount_throwIllegalArgumentException(){
        //given
        ClientModel client = new ClientModel("Michał", "Zakrzewski", 26, 50000, "michal@gmail.com");
        clients.add(client);
        int amount = 0;
        // when/then
        Assertions.assertThrows(IllegalArgumentException.class, () -> bankService.withdrawAmount(client.getEmailAddress(), amount));
    }

    @Test
    public void withdrawAmount_amountBiggerThanBalance_throwNotEnoughFundsException(){
        //given
        ClientModel client = new ClientModel("Michał", "Zakrzewski", 26, 50000, "michal@gmail.com");
        clients.add(client);
        int amount = 100000;
        // when/then
        Assertions.assertThrows(NotEnoughFundsException.class, () -> bankService.withdrawAmount(client.getEmailAddress(), amount));
    }

    @Test
    public void withdrawAmount_incorrectEmail_throwNoSuchElementException(){
        //given
        String email = "incorect_email@o2.pl";
        int amount = 1000;
        //when/then
        Assertions.assertThrows(NoSuchElementException.class, () -> bankService.withdrawAmount(email, amount));
    }

    @Test
    public void withdrawAmount_upperCaseEmail_balanceChangedCorrectly(){
        //given
        String emailAddress = "MICHAL@GMAIL.COM";
        ClientModel client = new ClientModel("Michał", "Zakrzewski", 26, 50000, "michal@gmail.com");
        clients.add(client);
        //when

        bankService.withdrawAmount(emailAddress, 20000);
        //then
        assertEquals("michal@gmail.com", client.getEmailAddress());
    }

    @Test
    public void withdrawAmount_nullEmail_throwIllegalArgumentException(){
        //given
        String email = null;
        int amount = 1000;
        //when/then
        Assertions.assertThrows(IllegalArgumentException.class, () -> bankService.withdrawAmount(email, amount));
    }

    @Test
    public void withdrawAmount_correctFloatingPointAmount_balanceChangedCorrectly(){
        //given
        ClientModel client = new ClientModel("Michał", "Zakrzewski", 26, 50000, "michal@gmail.com");
        clients.add(client);
        //when
        bankService.withdrawAmount(client.getEmailAddress(), 5000.50);
        //then
        assertEquals(44999.50, client.getBalance());
    }

}
