package com.zakrzewski;


import com.zakrzewski.entity.Account;
import com.zakrzewski.entity.Client;
import com.zakrzewski.repositories.ClientRepository;
import com.zakrzewski.services.BankService;
import com.zakrzewski.services.NotEnoughFundsException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class BankServiceTest {
    private BankService bankService;
    private ClientRepository repository;

    @BeforeEach
    public void setup() {
        repository = mock(ClientRepository.class);
        bankService = new BankService(repository);
    }

    /*@Test
    public void verifyIfUserIsAddingCorrectlyToTheRepository(){
        //given
        Client client = new Client("Michał", "michal@gmail.com", 500);
        Client expectedClient = new Client("Michał", "michal@gmail.com", 500);
        //when
        inMemoryClientRepository.addClient(client);
        Client actualClient = clients.stream()
                .findFirst()
                .get();
        //then
        assertEquals(expectedClient, actualClient);
    }*/

    @Test
    public void transferAmount_allParamsOk_fundsTransferred() {
        //given
        Client clientFrom = new Client("Michał", "michal@gmail.com", Collections.singletonList(new Account(1000, "PLN")));
        Client clientTo = new Client("Client", "client@gmail.com", Collections.singletonList(new Account(500, "PLN")));
        final double amountToTransfer = 100;
        when(repository.findClientByEmail(clientFrom.getEmailAddress())).thenReturn(clientFrom);
        when(repository.findClientByEmail(clientTo.getEmailAddress())).thenReturn(clientTo);

        //when
        bankService.transferAmount(clientFrom.getEmailAddress(), clientTo.getEmailAddress(), amountToTransfer);
        //then
        Client expectedClientFrom = new Client("Michał", "michal@gmail.com", Collections.singletonList(new Account(900, "PLN")));
        Client expectedClientTo = new Client("Client", "client@gmail.com", Collections.singletonList(new Account(600, "PLN")));
        verify(repository).saveClient(expectedClientFrom);
        verify(repository).saveClient(expectedClientTo);

        /*SoftAssertions softAssertions = new SoftAssertions();
        softAssertions
                .assertThat(clientFrom.getBalance())
                .isEqualTo(900);
        softAssertions
                .assertThat(clientTo.getBalance())
                .isEqualTo(600);
        softAssertions.assertAll();
        assertEquals(40000, clientFrom.getBalance());
        assertEquals(20000, clientTo.getBalance() );*/
    }

    @Test
    public void transfer_sendAllFunds_fundsTransferred(){
        //given
        Client clientFrom = new Client("Michał",  "michal@gmail.com", Collections.singletonList(new Account(1000, "PLN")));
        Client clientTo = new Client("Client", "client@gmail.com", Collections.singletonList(new Account(500, "PLN")));
        final double amountToTransfer = 1000;
        when(repository.findClientByEmail(clientFrom.getEmailAddress())).thenReturn(clientFrom);
        when(repository.findClientByEmail(clientTo.getEmailAddress())).thenReturn(clientTo);
        //when
        bankService.transferAmount(clientFrom.getEmailAddress(), clientTo.getEmailAddress(), amountToTransfer);
        //then
        Client expectedClientFrom = new Client("Michał", "michal@gmail.com", Collections.singletonList(new Account(0, "PLN")));
        Client expectedClientTo = new Client("Client", "client@gmail.com", Collections.singletonList(new Account(1500, "PLN")));
        verify(repository).saveClient(expectedClientFrom);
        verify(repository).saveClient(expectedClientTo);

    }

    @Test
    public void transfer_notEnoughFunds_throwNotEnoughFundException(){
        //given
        Client clientFrom = new Client("Michał", "michal@gmail.com", Collections.singletonList(new Account(1000, "PLN")));
        Client clientTo = new Client("Client", "client@gmail.com", Collections.singletonList(new Account(500, "PLN")));
        final double amountToTransfer = 10000;
        when(repository.findClientByEmail(clientFrom.getEmailAddress())).thenReturn(clientFrom);
        when(repository.findClientByEmail(clientTo.getEmailAddress())).thenReturn(clientFrom);
        // when/then
        assertThrows(NotEnoughFundsException.class, () -> bankService.transferAmount(clientFrom.getEmailAddress(), clientTo.getEmailAddress(), amountToTransfer));
    }

    @Test
    public void transfer_negativeAmount_throwIllegalArgumentException(){
        //given
        Client clientFrom = new Client("Michał", "michal@gmail.com", Collections.singletonList(new Account(1000, "PLN")));
        Client clientTo = new Client("Client", "client@gmail.com",  Collections.singletonList(new Account(500, "PLN")));
        final double amountToTransfer = -5000;
        // when/then
        assertThrows(
                IllegalArgumentException.class,
                () -> bankService.transferAmount(clientFrom.getEmailAddress(), clientTo.getEmailAddress(), amountToTransfer));
    }

    @Test
    public void transfer_toSameClient_thrownIllegalArgumentException(){
        //given
        Client clientFrom = new Client("Michał", "michal@gmail.com", Collections.singletonList(new Account(1000, "PLN")));
        final double amountToTransfer = 1000;
        // when/then
        assertThrows(IllegalArgumentException.class, () -> bankService.transferAmount(clientFrom.getEmailAddress(), clientFrom.getEmailAddress(), amountToTransfer));
    }

    @Test
    public void withdrawAmount_correctAmount_balanceChangedCorrectly(){
        //given
        Client client = new Client("Michał", "michal@gmail.com", Collections.singletonList(new Account(100, "PLN")));
        when(repository.findClientByEmail(client.getEmailAddress())).thenReturn(client);
        //when
        bankService.withdrawAmount(client.getEmailAddress(), 50);
        //then
        Client expectedClient = new Client("Michał", "michal@gmail.com", Collections.singletonList(new Account(50, "PLN")));
        verify(repository).saveClient(expectedClient);
        assertEquals(50, expectedClient.getBalance());
    }

    @Test
    public void withdrawAmount_allBalance_balanceSetToZero(){
        //given
        Client client = new Client("Michał", "michal@gmail.com", Collections.singletonList(new Account(1000, "PLN")));
        when(repository.findClientByEmail(client.getEmailAddress())).thenReturn(client);
        //when
        bankService.withdrawAmount(client.getEmailAddress(), 1000);
        //then
        Client expectedClient = new Client("Michał", "michal@gmail.com", Collections.singletonList(new Account(0, "PLN")));
        verify(repository).saveClient(expectedClient);
        assertEquals(0, expectedClient.getBalance());
    }

    @Test
    public void withdrawAmount_negativeAmount_throwIllegalArgumentException(){
        //given
        Client client = new Client("Michał", "michal@gmail.com",  Collections.singletonList(new Account(1000, "PLN")));
        int amount = -10000;
        // when/then
        Assertions.assertThrows(IllegalArgumentException.class, () -> bankService.withdrawAmount(client.getEmailAddress(), amount));
    }
   /*
    @Test
    public void withdrawAmount_zeroAmount_throwIllegalArgumentException(){
        //given
        Client client = new Client("Michał", "michal@gmail.com",  50000);
        clients.add(client);
        int amount = 0;
        // when/then
        Assertions.assertThrows(IllegalArgumentException.class, () -> bankService.withdrawAmount(client.getEmailAddress(), amount));
    }

    @Test
    public void withdrawAmount_amountBiggerThanBalance_throwNotEnoughFundsException(){
        //given
        Client client = new Client("Michał", "michal@gmail.com",  50000);
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
        Client client = new Client("Michał", "michal@gmail.com",  50000);
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
        Client client = new Client("Michał", "michal@gmail.com",  50000);
        clients.add(client);
        //when
        bankService.withdrawAmount(client.getEmailAddress(), 5000.50);
        //then
        assertEquals(44999.50, client.getBalance());
    }
*/
}
