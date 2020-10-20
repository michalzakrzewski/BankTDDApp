package com.zakrzewski;

import com.zakrzewski.entity.Account;
import com.zakrzewski.entity.Client;
import com.zakrzewski.services.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class MainApp implements CommandLineRunner {
    private BankService bankService;

    @Autowired
    public MainApp(BankService bankService) {
        this.bankService = bankService;
    }

    public static void main(String[] args) {
        SpringApplication.run(MainApp.class, args);
    }
    @Override
    public void run(String... args) throws Exception {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("1 - add user");
                System.out.println("2 - find user");
                System.out.println("3 - exit app");
                System.out.println("4 - transfer amount");
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
        Account account = new Account(balance, "PLN");
        List<Account> accounts = Arrays.asList(account);
        bankService.saveClient(new Client(firstName, emailAddress, accounts));
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
