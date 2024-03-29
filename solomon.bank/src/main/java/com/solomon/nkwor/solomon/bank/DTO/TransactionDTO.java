package com.solomon.nkwor.solomon.bank.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO {

    private String transactionType;
    private BigDecimal amount;
    private String accountNumber;
    private String debitedAccountNumber;
    private BigDecimal accountBalance;
    private BigDecimal sourceAccountBalance;
    private String status;
    private LocalDate transactionTime;
}
