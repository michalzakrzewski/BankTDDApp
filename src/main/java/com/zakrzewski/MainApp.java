package com.zakrzewski;

import com.zakrzewski.models.ClientModel;
import com.zakrzewski.repositories.ClientRepository;
import com.zakrzewski.repositories.JDBCClientRepository;
import com.zakrzewski.services.BankService;

import java.util.Scanner;

public class MainApp {
    private BankService bankService;

    public static void main(String[] args) {
        new MainApp().run();
    }

    public void run(){
        ClientRepository clientRepository = new JDBCClientRepository();
        bankService = new BankService(clientRepository);

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
            }
        }
    }

    private void printUser(Scanner scanner) {
        System.out.println("Enter email:");
        final String mail = scanner.next();
        System.out.println(bankService.findClientByEmailAddress(mail));
    }


    private void addUser(Scanner scanner) {
        System.out.println("Enter name:");
        final String firstName = scanner.next();
        System.out.println("Enter emailAddress:");
        final String emailAddress = scanner.next();
        System.out.println("Enter balance:");
        final double balance = scanner.nextDouble();
        bankService.saveClient(new ClientModel(firstName, emailAddress, balance));
    }

    private void transferAmount(Scanner scanner){
        System.out.println("Transfer amount:");
        System.out.print("From email: ");
        String fromEmail = scanner.next();
        System.out.print("To email: ");
        String toEmail = scanner.next();
        System.out.print("balance:");
        double amount = scanner.nextDouble();
        bankService.transferAmount(fromEmail, toEmail, amount);
    }

}
