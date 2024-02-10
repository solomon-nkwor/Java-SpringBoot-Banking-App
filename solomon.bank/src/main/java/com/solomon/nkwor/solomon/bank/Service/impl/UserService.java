package com.solomon.nkwor.solomon.bank.Service.impl;

import com.solomon.nkwor.solomon.bank.DTO.BankResponseDTO;
import com.solomon.nkwor.solomon.bank.DTO.CreditDebitRequestDTO;
import com.solomon.nkwor.solomon.bank.DTO.EnquiryRequest;
import com.solomon.nkwor.solomon.bank.DTO.UserRequestDTO;

public interface UserService {
    BankResponseDTO createAccount(UserRequestDTO userRequest);

    BankResponseDTO balanceEnquiry(EnquiryRequest enquiryRequest);

    String nameEnquiry (EnquiryRequest enquiryRequest);

    BankResponseDTO creditAccount(CreditDebitRequestDTO creditRequest);
    BankResponseDTO debitAccount(CreditDebitRequestDTO debitRequest);

}
