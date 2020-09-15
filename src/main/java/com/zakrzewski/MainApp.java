package com.zakrzewski;

import com.zakrzewski.models.ClientModel;
import com.zakrzewski.repositories.InMemoryClientRepository;
import com.zakrzewski.services.BankService;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class MainApp {
    private BankService bankService;

    public static void main(String[] args) {
        new MainApp().run();
    }

    public void run(){
        InMemoryClientRepository inMemoryClientRepository = new InMemoryClientRepository(new HashSet<>());
        bankService = new BankService(inMemoryClientRepository);

        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("1 - add user");
                System.out.println("2 - find user");
                System.out.println("3 - exit app");
                System.out.println("4 - transfer amount");
                System.out.println("5 - show all clients");
                final String next = scanner.next();
                if (next.equals("1")) {
                    addUser(scanner);
                }
                if (next.equals("2")) {
                    printUser(scanner);
                }
                if (next.equals("3")) {
                    break;
                }
                if (next.equals("4")){
                    transferAmount(scanner);
                }
                if (next.equals("5")){
                    showAllClients();
                }
            }
        }
    }

    private void printUser(Scanner scanner) {
        System.out.println("Enter email:");
        final String mail = scanner.next();
        System.out.println(bankService.findClientByEmailAddress(mail));
    }


    private void addUser(Scanner scanner) {
        System.out.println("Enter firstName:");
        final String firstName = scanner.next();
        System.out.println("Enter lastName:");
        final String lastName = scanner.next();
        System.out.println("Enter age:");
        final int age = scanner.nextInt();
        System.out.println("Enter amount:");
        final double amount = scanner.nextDouble();
        System.out.println("Enter emailAddress:");
        final String emailAddress = scanner.next();
        bankService.saveClient(new ClientModel(firstName, lastName, age, amount, emailAddress));
    }

    private void transferAmount(Scanner scanner){
        System.out.println("Transfer amount:");
        System.out.print("From email: ");
        String fromEmail = scanner.next();
        System.out.print("To email: ");
        String toEmail = scanner.next();
        System.out.print("Amount:");
        double amount = scanner.nextDouble();
        bankService.transferAmount(fromEmail, toEmail, amount);
    }

    private void showAllClients(){
        bankService.findAllClients();
    }
}
