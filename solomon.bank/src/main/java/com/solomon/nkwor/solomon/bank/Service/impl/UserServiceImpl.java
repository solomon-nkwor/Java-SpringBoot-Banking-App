package com.solomon.nkwor.solomon.bank.Service.impl;

import com.solomon.nkwor.solomon.bank.DTO.AccountInfoDTO;
import com.solomon.nkwor.solomon.bank.DTO.BankResponseDTO;
import com.solomon.nkwor.solomon.bank.DTO.UserRequestDTO;
import com.solomon.nkwor.solomon.bank.Model.User;
import com.solomon.nkwor.solomon.bank.Repository.UserRepository;
import com.solomon.nkwor.solomon.bank.Utils.AccountUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository){

        this.userRepository = userRepository;
    }
    @Override
    public BankResponseDTO createAccount(UserRequestDTO userRequest) {

        if (userRepository.existsByEmail(userRequest.getEmail())){
            BankResponseDTO response = BankResponseDTO.builder()
                    .responseCode(AccountUtils.ACCOUNT_EXISTS_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_EXISTS_MESSAGE)
                    .accountInfo(null)
                    .build();
        }
        User user = User.builder()
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .middleName(userRequest.getMiddleName())
                .email(userRequest.getEmail())
                .gender(userRequest.getGender())
                .address(userRequest.getAddress())
                .stateOfOrigin(userRequest.getStateOfOrigin())
                .phoneNumber(userRequest.getPhoneNumber())
                .alternativeNumber(userRequest.getAlternativeNumber())
                .accountNumber(AccountUtils.generateAccountNumber())
                .accountBalance(BigDecimal.ZERO)
                .status("ACTIVE")
                .build();
        User savedUser = userRepository.save(user);
        return BankResponseDTO.builder()
                .responseCode(AccountUtils.ACCOUNT_CREATION_SUCCESS_STATUS)
                .responseMessage(AccountUtils.ACCOUNT_CREATION_SUCCESS_MESSAGE)
                .accountInfo(AccountInfoDTO.builder()
                        .accountBalance(savedUser.getAccountBalance())
                        .accountNumber(savedUser.getAccountNumber())
                        .accountName(savedUser.getFirstName() + " " + savedUser.getLastName() + " " + savedUser.getMiddleName())
                        .build())
                .build();
    }
}
