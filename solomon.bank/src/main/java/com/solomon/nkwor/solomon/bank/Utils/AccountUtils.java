package com.solomon.nkwor.solomon.bank.Utils;

import com.solomon.nkwor.solomon.bank.Service.impl.UserService;
import com.solomon.nkwor.solomon.bank.Service.impl.UserServiceImpl;

public class AccountUtils {


    public static final String ACCOUNT_EXISTS_CODE = "001";
    public static final String ACCOUNT_EXISTS_MESSAGE = "User with the same email address already exists";

    public static final String ACCOUNT_CREATION_SUCCESS_STATUS = "002";
    public static final String ACCOUNT_CREATION_SUCCESS_MESSAGE = "Account successfully created";

    public static final String OPEN_ACCOUNT_EMAIL_SUBJECT = "Account Creation - Welcome to the Family";
    public static final String OPEN_ACCOUNT_EMAIL_MESSAGE = "Congratulations! You have successfully opened an account with us!\n" +
            "Your Account Details: ";


    public static String generateAccountNumber() {
        // this class randomly generates 10-digit account numbers following a set of rules
        // in this case, we'd be generating numbers based on some fixed values (7656) and 6 other random numbers
        final int fixedNumber = 7656;
        int min = 100_000;
        int max = 999_999;

        // generating a random number between min and max
        // the Math.random method generates values between 0 - 9

        int randomNumber = (int) Math.floor(Math.random() * (max - min + 1) + min);
        // convert fixedNumber and randomNumber to string and concatenate

        String fixedString = String.valueOf(fixedNumber);
        String randomString = String.valueOf(randomNumber);

        return fixedString + randomString;
    }

}
