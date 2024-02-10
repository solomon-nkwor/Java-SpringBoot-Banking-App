package com.solomon.nkwor.solomon.bank.Service.impl;

import com.solomon.nkwor.solomon.bank.Converter.UserConverter;
import com.solomon.nkwor.solomon.bank.DTO.*;
import com.solomon.nkwor.solomon.bank.Model.User;
import com.solomon.nkwor.solomon.bank.Repository.UserRepository;
import com.solomon.nkwor.solomon.bank.Utils.AccountUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private EmailService emailService;

    private UserConverter userConverter;

    public UserServiceImpl(UserRepository userRepository, EmailService emailService, UserConverter userConverter){

        this.userRepository = userRepository;
        this.emailService = emailService;
        this.userConverter = userConverter;
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
        EmailDetails emailDetails = EmailDetails.builder()
                .recipient(savedUser.getEmail())
                .subject(AccountUtils.OPEN_ACCOUNT_EMAIL_SUBJECT)
                .messageBody(AccountUtils.OPEN_ACCOUNT_EMAIL_MESSAGE + "\n"
                        + "Account number: " + savedUser.getAccountNumber() + "\n"
                        + "Account name: " + savedUser.getFirstName() + " "
                        + savedUser.getMiddleName() + " " + savedUser.getLastName() + "\n"
                        + "Account balance: " + savedUser.getAccountBalance())
                .build();
        emailService.sendEmailAlerts(emailDetails);
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

    @Override
    public BankResponseDTO balanceEnquiry(EnquiryRequest enquiryRequest) {
        boolean isAccountExists = userRepository.existsByAccountNumber(enquiryRequest.getAccountNumber());

        if (!isAccountExists){
            return BankResponseDTO.builder()
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE)
                    .accountInfo(null)
                    .build();
        }
        User foundUser = userRepository.findByAccountNumber(enquiryRequest.getAccountNumber());

        return BankResponseDTO.builder()
                .responseCode(AccountUtils.ACCOUNT_FOUND_CODE)
                .responseMessage(AccountUtils.ACCOUNT_FOUND_MESSAGE)
                .accountInfo(AccountInfoDTO.builder()
                        .accountBalance(foundUser.getAccountBalance())
                        .accountName(foundUser.getFirstName() + " " + foundUser.getMiddleName() + " " + foundUser.getLastName())
                        .accountNumber(foundUser.getAccountNumber())
                        .build())
                .build();
    }

    @Override
    public String nameEnquiry(EnquiryRequest enquiryRequest) {
        boolean isAccountExist = userRepository.existsByAccountNumber(enquiryRequest.getAccountNumber());

        if (!isAccountExist){
            return AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE;
        }
        User foundUser = userRepository.findByAccountNumber(enquiryRequest.getAccountNumber());

        return foundUser.getFirstName() + " " + foundUser.getMiddleName() + " " + foundUser.getLastName();
    }

    @Override
    public BankResponseDTO creditAccount(CreditDebitRequestDTO creditRequest) {
        boolean isAccountExist = userRepository.existsByAccountNumber(creditRequest.getAccountNumber());

        if (!isAccountExist){
            return BankResponseDTO.builder()
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE)
                    .accountInfo(null)
                    .build();
        }

        User creditToUser = userRepository.findByAccountNumber(creditRequest.getAccountNumber());

        creditToUser.setAccountBalance(creditToUser.getAccountBalance().add(creditRequest.getAmount()));
        User creditedUser = userRepository.save(creditToUser);
        EmailDetails emailDetails = EmailDetails.builder()
                .recipient(creditedUser.getEmail())
                .subject(AccountUtils.CREDIT_ACCOUNT_EMAIL_SUBJECT)
                .messageBody(AccountUtils.CREDIT_ACCOUNT_EMAIL_MESSAGE + "\n"
                        + "Account number: " + creditedUser.getAccountNumber() + "\n"
                        + "Account name: " + creditedUser.getFirstName() + " "
                        + creditedUser.getMiddleName() + " " + creditedUser.getLastName() + "\n"
                        + " Transaction type: Deposit" + "\n"
                        + "Amount credited: " + creditRequest.getAmount() + "\n"
                        + "Account balance: " + creditedUser.getAccountBalance())
                .build();
        emailService.sendEmailAlerts(emailDetails);
        return BankResponseDTO.builder()
                .responseCode(AccountUtils.CREDITED_SUCCESS_CODE)
                .responseMessage(AccountUtils.CREDITED_SUCCESS_MESSAGE)
                .accountInfo(AccountInfoDTO.builder()
                        .accountName(creditToUser.getFirstName() + " " + creditToUser.getLastName())
                        .accountNumber(creditToUser.getAccountNumber())
                        .accountBalance(creditedUser.getAccountBalance())
                        .build())
                .build();
    }

    @Override
    public BankResponseDTO debitAccount(CreditDebitRequestDTO debitRequest) {
        boolean isAccountExist = userRepository.existsByAccountNumber(debitRequest.getAccountNumber());

        if (!isAccountExist){
            return BankResponseDTO.builder()
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE)
                    .accountInfo(null)
                    .build();
        }

        User debitUser = userRepository.findByAccountNumber(debitRequest.getAccountNumber());
//        to run a comparison with Big decimal values, you have to invoke the 'intvalue()' method
//        because normal comparisons don't work with big decimals

        if (debitUser.getAccountBalance().intValue() < debitRequest.getAmount().intValue()){
            return BankResponseDTO.builder()
                    .responseCode(AccountUtils.INSUFFICIENT_FUNDS_CODE)
                    .responseMessage(AccountUtils.INSUFFICIENT_FUNDS_MESSAGE)
                    .accountInfo(null)
                    .build();
        }
        debitUser.setAccountBalance(debitUser.getAccountBalance().subtract(debitRequest.getAmount()));
        User debitedtedUser = userRepository.save(debitUser);
            EmailDetails emailDetails = EmailDetails.builder()
                    .recipient(debitedtedUser.getEmail())
                    .subject(AccountUtils.DEBIT_ACCOUNT_EMAIL_SUBJECT)
                    .messageBody(AccountUtils.DEBIT_ACCOUNT_EMAIL_MESSAGE + "\n"
                            + "Account number: " + debitedtedUser.getAccountNumber() + "\n"
                            + "Account name: " + debitedtedUser.getFirstName() + " "
                            + debitedtedUser.getMiddleName() + " " + debitedtedUser.getLastName() + "\n"
                            + " Transaction type: Withdrawal \n"
                            + "Amount debited: " + debitRequest.getAmount() + "\n"
                            + "Account balance: " + debitedtedUser.getAccountBalance())
                    .build();
            emailService.sendEmailAlerts(emailDetails);
        return BankResponseDTO.builder()
                .responseCode(AccountUtils.DEBITED_SUCCESS_CODE)
                .responseMessage(AccountUtils.DEBITED_SUCCESS_MESSAGE)
                .accountInfo(AccountInfoDTO.builder()
                        .accountName(debitUser.getFirstName() + " " + debitUser.getLastName())
                        .accountNumber(debitUser.getAccountNumber())
                        .accountBalance(debitedtedUser.getAccountBalance())
                        .build())
                .build();
// the next thing to do is to incorporate transfer services from one account to the other
    }

    @Override
    public List<GetUserDTO> getAllUsers() {
        log.info("Get all Users Started");
        List<User> allUsers =userRepository.findAll();
        log.info("Get all users completed");
        return allUsers.stream().map(user -> userConverter.convertUserToDTO(user))
            .collect(Collectors.toList());

    }

    @Override
    public GetUserDTO getUsersByAccountNumber(String accountNumber) {
        log.info("Get User by Account Number Started");
        User user = userRepository.findByAccountNumber(accountNumber);
        if(user != null){
            log.info("Get User by Account Number completed");
            return userConverter.convertUserToDTO(user);
        }
        else{
            return null;
        }

    }

    @Override
    public GetUserDTO getUsersByID(String id) {
        log.info("getUserByID Started");
        User user = userRepository.findById(id).orElse(null);
        if(user != null){
            log.info("getUserByID completed");
            return userConverter.convertUserToDTO(user);
        }
        else{
            return null;
        }
    }


}