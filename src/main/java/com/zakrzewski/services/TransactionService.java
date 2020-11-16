package com.zakrzewski.services;

import com.zakrzewski.dto.TransactionRequest;
import com.zakrzewski.entity.Transaction;
import com.zakrzewski.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
public class TransactionService {

    private TransactionRepository transactionRepository;
    private AccountService accountService;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository, AccountService accountService) {
        this.transactionRepository = transactionRepository;
        this.accountService = accountService;
    }

    public void createTransaction(TransactionRequest transactionRequest){
        accountService.transferAmount(transactionRequest.getFromAccountId(), transactionRequest.getToAccountId(), transactionRequest.getAmount());
        Transaction transaction = new Transaction();
        transaction.setAmount(transactionRequest.getAmount());
        transaction.setCurrency(transactionRequest.getCurrency());
        transaction.setFromAccountId(transactionRequest.getFromAccountId());
        transaction.setToAccountId(transactionRequest.getToAccountId());
        transaction.setTransactionDate(OffsetDateTime.now());
        transactionRepository.save(transaction);
    }
}
