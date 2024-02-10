package com.solomon.nkwor.solomon.bank.DTO;

import lombok.Data;

@Data
public class GetUserDTO {
    private String id;
    private String email;
    private String firstName;
    private String lastName;
    private String accountNumber;
    private String accountBalance;
}
