package com.arkaces.aces_marketplace_api.user;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    
    private String emailAddress;

    private Boolean isEmailAddressVerified;

    private String passwordHash;
    
    private String passwordSalt;
    
}
