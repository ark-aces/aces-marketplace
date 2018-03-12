package com.arkaces.aces_marketplace_api.registration;

import com.arkaces.aces_marketplace_api.account.AccountEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(of = "pid")
@Entity
@Table(name = "registrations")
public class RegistrationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pid;
    
    String id;
    
    LocalDateTime createdAt;

    @OneToOne
    @JoinColumn(name ="account_pid")
    AccountEntity accountEntity;
    
}
