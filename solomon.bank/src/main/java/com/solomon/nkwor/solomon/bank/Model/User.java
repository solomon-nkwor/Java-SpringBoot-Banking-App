package com.solomon.nkwor.solomon.bank.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Entity
@Data
@NoArgsConstructor
@Table(name = "Bank_Users")
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", length = 64)
    private String id;
    @Column(name = "first_name", length = 200)
    private String firstName;
    @Column(name = "last_name", length = 200)
    private String lastName;
    @Column(name = "middle_name", length = 200)
    private String middleName;
    @Column(name = "email", length = 200, unique = true)
    private String email;
    @Column(name = "password", length = 200, unique = true)
    private String password;
    @Column(name = "gender", length = 200)
    private String gender;
    @Column(name = "address", length = 200)
    private String address;
    @Column(name = "state_of_origin", length = 200)
    private String stateOfOrigin;
    @GeneratedValue
    @Column(name = "account_number", length = 200, unique = true)
    private String accountNumber;
    @Column(name = "account_balance", length = 200)
    private BigDecimal accountBalance;
    @Column(name = "phone_number", length = 200)
    private String phoneNumber;
    @Column(name = "alternative_number", length = 200)
    private String alternativeNumber;
    @Column(name = "status", length = 200)
    private String status;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime modifiedAt;
}
