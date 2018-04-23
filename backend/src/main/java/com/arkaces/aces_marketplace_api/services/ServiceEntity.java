package com.arkaces.aces_marketplace_api.services;

import com.arkaces.aces_marketplace_api.account.AccountEntity;
import com.arkaces.aces_marketplace_api.contract.ContractEntity;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "services")
public class ServiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pid;

    private Boolean isTestnet = true;
    
    private String id;
    private String url;
    private String name;
    private String version;
    private String description;
    private String websiteUrl;

    private BigDecimal flatFee;
    private BigDecimal percentFee;

    private LocalDateTime createdAt;
    
    @ManyToOne
    @JoinColumn(name = "account_pid")
    private AccountEntity accountEntity;

    @OneToMany(mappedBy = "serviceEntity")
    private List<ContractEntity> contractEntities;

    @OneToMany(mappedBy = "serviceEntity", cascade = CascadeType.ALL)
    private List<ServiceCapacityEntity> serviceCapacityEntities;
}
