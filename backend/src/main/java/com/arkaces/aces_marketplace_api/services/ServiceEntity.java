package com.arkaces.aces_marketplace_api.services;

import com.arkaces.aces_marketplace_api.account.AccountEntity;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "services")
public class ServiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pid;
    
    private String id;
    private String url;
    private String name;
    private String version;
    private String description;
    private String websiteUrl;
    
    private LocalDateTime createdAt;
    
    @ManyToOne
    @JoinColumn(name = "account_pid")
    private AccountEntity accountEntity;
}
