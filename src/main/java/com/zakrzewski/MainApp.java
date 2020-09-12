package com.zakrzewski;

import com.zakrzewski.models.ClientModel;
import com.zakrzewski.repositories.InMemoryClientRepository;
import com.zakrzewski.services.BankService;

import java.util.HashSet;
import java.util.Scanner;

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
        System.out.println("Enter emailAdress:");
        final String emailAddress = scanner.next();
        bankService.saveClient(new ClientModel(firstName, lastName, age, amount, emailAddress));
    }
}
