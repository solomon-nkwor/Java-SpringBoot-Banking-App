package com.solomon.nkwor.solomon.bank.Service.impl;

import com.solomon.nkwor.solomon.bank.DTO.*;

import java.math.BigDecimal;
import java.util.List;

public interface UserService {
    BankResponseDTO createAccount(UserRequestDTO userRequest);

    BankResponseDTO balanceEnquiry(EnquiryRequest enquiryRequest);

    String nameEnquiry (EnquiryRequest enquiryRequest);

    BankResponseDTO creditAccount(CreditDebitRequestDTO creditRequest);
    BankResponseDTO debitAccount(CreditDebitRequestDTO debitRequest);

    List<GetUserDTO> getAllUsers();

    GetUserDTO getUsersByID(String id);
    GetUserDTO getUsersByAccountNumber(String accountNumber);

    BankResponseDTO transfer(TransferDTO transferRequest);

}
