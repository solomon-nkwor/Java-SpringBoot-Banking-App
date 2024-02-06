package com.solomon.nkwor.solomon.bank.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String Id;
    private String firstName;
    private String lastName;
    private String middleName;
    private String email;
    private String gender;
    private String address;
    private String stateOfOrigin;
    private String phoneNumber;
    private String alternativeNumber;
}
