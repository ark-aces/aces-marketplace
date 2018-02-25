package com.arkaces.aces_marketplace_api.account;

import com.arkaces.aces_marketplace_api.services.ServiceEntity;
import com.arkaces.aces_marketplace_api.user.UserEntity;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
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

    @OneToMany(cascade = CascadeType.ALL)
    private List<UserEntity> userEntities;
    
    @OneToMany(cascade = CascadeType.ALL)
    private List<ApiKeyEntity> apiKeyEntities;
    
    @OneToMany(cascade = CascadeType.ALL)
    private List<ServiceEntity> serviceEntities;
    
}
