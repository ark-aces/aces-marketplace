package com.arkaces.aces_marketplace_api.account;

import com.arkaces.aces_marketplace_api.registration.RegistrationEntity;
import com.arkaces.aces_marketplace_api.services.ServiceEntity;
import com.arkaces.aces_marketplace_api.user.UserEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@EqualsAndHashCode(of = "pid")
@Entity
@Table(name = "accounts")
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pid;
    
    private String id;
    
    private String contactEmailAddress;
    
    private String emailAddressVerificationCode;
    
    private Boolean isContactEmailAddressVerified;
    
    private String arkWalletAddress;
    
    private LocalDateTime createdAt;

    @OneToOne(mappedBy = "accountEntity")
    private RegistrationEntity registrationEntity;
    
    @OneToMany(mappedBy = "accountEntity")
    private List<UserEntity> userEntities;
    
    @OneToMany(mappedBy = "accountEntity")
    private List<ServiceEntity> serviceEntities;
    
}
