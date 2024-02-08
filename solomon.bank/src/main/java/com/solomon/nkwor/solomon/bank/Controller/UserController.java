package com.solomon.nkwor.solomon.bank.Controller;

import com.solomon.nkwor.solomon.bank.DTO.BankResponseDTO;
import com.solomon.nkwor.solomon.bank.DTO.UserRequestDTO;
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
