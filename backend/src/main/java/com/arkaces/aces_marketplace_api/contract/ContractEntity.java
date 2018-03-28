package com.arkaces.aces_marketplace_api.contract;

import com.arkaces.aces_marketplace_api.account.AccountEntity;
import com.arkaces.aces_marketplace_api.services.ServiceEntity;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "contracts")
public class ContractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pid;

    private String id;
    private LocalDateTime createdAt;
    private String argumentsJson;
    
    private String remoteContractId;
    private String status;
    
    @ManyToOne
    @JoinColumn(name = "account_pid")
    private AccountEntity accountEntity;

    @ManyToOne
    @JoinColumn(name = "service_pid")
    private ServiceEntity serviceEntity;
    
}
