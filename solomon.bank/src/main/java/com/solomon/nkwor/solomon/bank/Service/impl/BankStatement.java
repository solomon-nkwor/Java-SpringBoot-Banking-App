package com.solomon.nkwor.solomon.bank.Service.impl;

import com.solomon.nkwor.solomon.bank.Model.Transactions;
import com.solomon.nkwor.solomon.bank.Repository.TransactionRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
@AllArgsConstructor
public class BankStatement {

    private TransactionRepository transactionRepository;

//    public BankStatement(TransactionRepository transactionRepository){
//        this.transactionRepository = transactionRepository;
//    }

    public List<Transactions> generateStatement(String accountNumber, String startDate, String endDate){
        LocalDate start = LocalDate.parse(startDate, DateTimeFormatter.ISO_DATE);
        LocalDate end = LocalDate.parse(endDate, DateTimeFormatter.ISO_DATE);

        return transactionRepository.findAll().stream().filter(transactions -> transactions.getAccountNumber().equals(accountNumber))
                .filter(transactions -> transactions.getTransactionTime().isEqual(start)).filter(transactions -> transactions.getTransactionTime().isEqual(end)).toList();
    }

    private void designStatement(List<Transactions> transactions){

    }
}
