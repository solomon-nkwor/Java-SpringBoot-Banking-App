package com.solomon.nkwor.solomon.bank.Controller;

import com.solomon.nkwor.solomon.bank.DTO.BankResponseDTO;
import com.solomon.nkwor.solomon.bank.DTO.UserRequestDTO;
import com.solomon.nkwor.solomon.bank.Service.impl.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@Slf4j
public class UserController {
    UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }
    @PostMapping("/createuser")
    public BankResponseDTO createAccount(@RequestBody UserRequestDTO userRequest){
        log.info("Create user started");
        log.info("Create user completed");
        return userService.createAccount(userRequest);


    }

}
