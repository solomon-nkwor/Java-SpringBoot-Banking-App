package com.solomon.nkwor.solomon.bank.Controller;

import com.solomon.nkwor.solomon.bank.Model.Transactions;
import com.solomon.nkwor.solomon.bank.Service.impl.BankStatement;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bank-statement")
@Slf4j
public class TransactionController {

    private BankStatement bankStatement;

    @Value("${apiKey}")
    private String API_KEY;

    private static final String BAD_API_KEY = "{\"status\":\"Authorization Failed\", \"message\":\"Invalid API Key\"}";

    public TransactionController(BankStatement bankStatement){
        this.bankStatement = bankStatement;
    }
    @GetMapping
    public ResponseEntity<?> getBankStatement(@RequestHeader (value = "apiKey", required = false) String apiKey, @RequestParam String accountNumber, @RequestParam String startDate,
                                              @RequestParam String endDate){

        if(apiKey == null || !apiKey.equals(API_KEY)){
            log.error("Invalid API Key");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(BAD_API_KEY);
        };

        List<Transactions> transactions = bankStatement.generateStatement(accountNumber, startDate, endDate);
        return ResponseEntity.status(HttpStatus.OK).body(transactions);

    }
}
