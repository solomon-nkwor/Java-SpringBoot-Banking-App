package com.solomon.nkwor.solomon.bank.Service.impl;

import com.solomon.nkwor.solomon.bank.DTO.TransactionDTO;
import com.solomon.nkwor.solomon.bank.Model.Transactions;
import com.solomon.nkwor.solomon.bank.Repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TransactionServiceImpl implements TransactionService{

    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository){
        this.transactionRepository = transactionRepository;
    }

    @Override
    public void saveTransaction(TransactionDTO transactionDTO) {

        Transactions transactions = Transactions.builder()
                .transactionType(transactionDTO.getTransactionType())
                .amount(transactionDTO.getAmount())
                .accountNumber(transactionDTO.getAccountNumber())
                .debitedAccountNumber(transactionDTO.getDebitedAccountNumber())
                .status("Success")
                .transactionTime(transactionDTO.getTransactionTime())
                .build();

        transactionRepository.save(transactions);
        log.info("Transaction saved successfully");
    }
}
