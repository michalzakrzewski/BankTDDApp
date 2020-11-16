package com.zakrzewski.services;

import com.zakrzewski.dto.AccountRequest;
import com.zakrzewski.dto.AccountResponse;
import com.zakrzewski.entity.Account;
import com.zakrzewski.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }


    public void saveAccount(AccountRequest account) {
        Account newAccount = new Account();
        newAccount.setBalance(account.getBalance());
        newAccount.setCurrency(account.getCurrency());
        newAccount.setUserId(account.getUserId());
        accountRepository.save(newAccount);
    }

    public AccountResponse findAccountById(Long id){
        return accountRepository.findById(id)
                .map(account -> {
                    AccountResponse accountResponse = new AccountResponse();
                    accountResponse.setBalance(account.getBalance());
                    accountResponse.setCurrency(account.getCurrency());
                    accountResponse.setUserId(account.getUserId());
                    accountResponse.setId(account.getId());
                    return accountResponse;
                })
                .orElseThrow(() -> new IllegalArgumentException("Account with id: " + id + " not found"));
    }

    public void transferAmount(long fromAccountId, long toAccountId, double amount){
        validateAmount(amount);
        if (fromAccountId == toAccountId){
            throw new IllegalArgumentException("fromAccountId and toAccountId can't be equal! ");
        }
        Account fromAccount = accountRepository.getOne(fromAccountId);
        Account toAccount = accountRepository.getOne(toAccountId);
        if (fromAccount.getBalance() - amount >= 0){
            fromAccount.setBalance(fromAccount.getBalance() - amount);
            toAccount.setBalance(toAccount.getBalance() + amount);
        }else {
            throw new NotEnoughFundsException("Not enough funds");
        }

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

    }

    private void validateAmount(double amount) {
        if (amount <= 0){
            throw new IllegalArgumentException("Negative amount is not allowed!");
        }
    }
}
