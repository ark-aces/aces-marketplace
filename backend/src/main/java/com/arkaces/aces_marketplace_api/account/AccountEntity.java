package com.arkaces.aces_marketplace_api.account;

import com.arkaces.aces_marketplace_api.user.UserEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "accounts")
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToMany
    private List<UserEntity> users;

    private String contactEmailAddress;
    
    private String arkWalletAddress;
}
