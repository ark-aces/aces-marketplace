package com.arkaces.aces_marketplace_api.user;

import com.arkaces.aces_marketplace_api.account.AccountEntity;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pid;
    
    private String id;
    
    private String name;
    
    private String emailAddress;

    private Boolean isEmailAddressVerified;

    private String passwordHash;
    
    @ManyToOne
    @JoinColumn(name="account_id", nullable = false)
    private AccountEntity accountEntity;

}
