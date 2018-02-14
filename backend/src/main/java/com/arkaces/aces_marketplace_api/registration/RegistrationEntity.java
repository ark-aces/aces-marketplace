package com.arkaces.aces_marketplace_api.registration;

import com.arkaces.aces_marketplace_api.account.AccountEntity;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "registrations")
public class RegistrationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    String contactEmailAddress;
    
    String emailAddressVerificationCode;

    Boolean isEmailAddressVerified;

    LocalDateTime createdAt;

    @OneToOne
    AccountEntity createdAccountEntity;
}
