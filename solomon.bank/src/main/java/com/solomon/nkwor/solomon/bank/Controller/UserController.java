package com.solomon.nkwor.solomon.bank.Controller;

import com.solomon.nkwor.solomon.bank.DTO.*;
import com.solomon.nkwor.solomon.bank.Service.impl.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/user")
@Slf4j
public class UserController {
    @Value("${apiKey}")
    private String API_KEY;

    private static final String BAD_API_KEY = "{\"status\":\"Authorization Failed\", \"message\":\"Invalid API Key\"}";
    UserService userService;

    public UserController(UserService userService){

        this.userService = userService;
    }
    @PostMapping("/create-user")
    public ResponseEntity <?>  createAccount(@RequestHeader(value = "apiKey", required = false) String apiKey,
                                                 @Valid @RequestBody UserRequestDTO userRequest){
        if(apiKey == null || !apiKey.equals(API_KEY)){
            log.error("Invalid API Key");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(BAD_API_KEY);
        }

        log.info("Create user started");

        BankResponseDTO createdUser = userService.createAccount(userRequest);
        log.info("Create user completed");
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);


    }

    @GetMapping("/balance-enquiry")
    public ResponseEntity <?> balanceEnquiry(@RequestHeader(value = "apiKey", required = false) String apiKey,
                                             @Valid @RequestBody EnquiryRequest enquiryRequest){
        if(apiKey == null || !apiKey.equals(API_KEY)){
            log.error("Invalid API Key");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(BAD_API_KEY);
        }

        log.info("Get balance started");

        BankResponseDTO foundUser = userService.balanceEnquiry(enquiryRequest);
        log.info("Get Balance completed");
        return ResponseEntity.status(HttpStatus.FOUND).body(foundUser);

    }

    @GetMapping("/name-enquiry")
    public ResponseEntity <?> nameEnquiry(@RequestHeader(value = "apiKey", required = false) String apiKey,
                                             @Valid @RequestBody EnquiryRequest enquiryRequest){
        if(apiKey == null || !apiKey.equals(API_KEY)){
            log.error("Invalid API Key");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(BAD_API_KEY);
        }

        log.info("Name Enquiry started");

        String foundUser = userService.nameEnquiry(enquiryRequest);
        log.info("Name Enquiry completed");
        return ResponseEntity.status(HttpStatus.FOUND).body(foundUser);

    }

    @PostMapping("/credit-user")
    public ResponseEntity <?> creditUser (@RequestHeader(value = "apiKey", required = false) String apiKey,
                                          @Valid @RequestBody CreditDebitRequestDTO creditRequest){
        if(apiKey == null || !apiKey.equals(API_KEY)){
            log.error("Invalid API Key");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(BAD_API_KEY);
        }

        log.info("Credit user started");

        BankResponseDTO userToCredit = userService.creditAccount(creditRequest);
        log.info("Credit user completed");
        return ResponseEntity.status(HttpStatus.OK).body(userToCredit);
    }

    @PostMapping("/debit-user")
    public ResponseEntity <?> debitUser (@RequestHeader(value = "apiKey", required = false) String apiKey,
                                          @Valid @RequestBody CreditDebitRequestDTO debitRequest){
        if(apiKey == null || !apiKey.equals(API_KEY)){
            log.error("Invalid API Key");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(BAD_API_KEY);
        }

        log.info("Debit user started");

        BankResponseDTO userToCredit = userService.debitAccount(debitRequest);
        log.info("Debit user completed");
        return ResponseEntity.status(HttpStatus.OK).body(userToCredit);
    }

    @PostMapping("/transfer")
    public ResponseEntity<?> transfer(@RequestHeader(value = "apiKey", required = false) String apiKey,
                                      @Valid @RequestBody TransferDTO transferRequest){
        if(apiKey == null || !apiKey.equals(API_KEY)){
            log.error("Invalid API Key");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(BAD_API_KEY);
        }

        log.info("Transfer started");
        BankResponseDTO transfer = userService.transfer(transferRequest);
        log.info("Transfer complete");
        return ResponseEntity.status(HttpStatus.OK).body(transfer);
    }

    @GetMapping("/all-users")
    public ResponseEntity <?> getAllUsers (@RequestHeader(value = "apiKey", required = false) String apiKey){

        if(apiKey == null || !apiKey.equals(API_KEY)){
            log.error("Invalid API Key");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(BAD_API_KEY);
        }
        List<GetUserDTO> users = userService.getAllUsers();
        return ResponseEntity.status(HttpStatus.FOUND).body(users);
    }

    @GetMapping("/by-id/{id}")
    public ResponseEntity<?> getUsersById(@RequestHeader(value = "apiKey", required = false) String apiKey,
                                          @PathVariable String id){
        if(apiKey == null || !apiKey.equals(API_KEY)){
            log.error("Invalid API Key");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(BAD_API_KEY);
        }
        GetUserDTO user = userService.getUsersByID(id);
        return ResponseEntity.status(HttpStatus.FOUND).body(user);
    }

    @GetMapping("/by-account-number/{accountNumber}")
    public ResponseEntity<?> getUsersByAccountNumber(@RequestHeader(value = "apiKey", required = false) String apiKey,
                                                     @PathVariable String accountNumber){
        if (apiKey == null || !apiKey.equals(API_KEY)){
            log.error("Invalid API Key");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(BAD_API_KEY);
        }
        GetUserDTO user = userService.getUsersByAccountNumber(accountNumber);
        return ResponseEntity.status(HttpStatus.FOUND).body(user);
    }



    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler (MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex){
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return errors;
    }

}
