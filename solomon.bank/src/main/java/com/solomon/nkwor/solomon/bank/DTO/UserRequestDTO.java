package com.solomon.nkwor.solomon.bank.DTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO {
    @Valid

    @NotNull(message = "Firstname is required")
    @NotBlank(message = "Firstname is required")
    private String firstName;

    @NotNull(message = "lastname is required")
    @NotBlank(message = "lastname is required")
    private String lastName;

    @NotNull(message = "Middle name is required")
    @NotBlank(message = "Middle name is required")
    private String middleName;

    @NotNull(message = "email is required")
    @NotBlank(message = "email is required")
    private String email;

    @NotNull(message = "gender is required")
    @NotBlank(message = "gender is required")
    private String gender;

    @NotNull(message = "address is required")
    @NotBlank(message = "address is required")
    private String address;

    @NotNull(message = "State of Origin is required")
    @NotBlank(message = "State of Origin is required")
    private String stateOfOrigin;

    @NotNull(message = "phone number is required")
    @NotBlank(message = "phone number is required")
    private String phoneNumber;


    private String alternativeNumber;
}
