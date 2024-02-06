package com.solomon.nkwor.solomon.bank.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountInfoDTO {
    private String Id;
    private String accountName;
    private BigDecimal accountBalance;
    private String accountNumber;
}
