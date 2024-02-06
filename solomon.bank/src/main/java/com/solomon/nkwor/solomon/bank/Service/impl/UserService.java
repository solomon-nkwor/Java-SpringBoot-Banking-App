package com.solomon.nkwor.solomon.bank.Service.impl;

import com.solomon.nkwor.solomon.bank.DTO.BankResponseDTO;
import com.solomon.nkwor.solomon.bank.DTO.UserRequestDTO;

public interface UserService {
    BankResponseDTO createAccount(UserRequestDTO userRequest);

}
