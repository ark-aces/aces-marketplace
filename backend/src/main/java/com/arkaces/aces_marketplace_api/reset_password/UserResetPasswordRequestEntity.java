package com.arkaces.aces_marketplace_api.reset_password;

import com.arkaces.aces_marketplace_api.user.UserEntity;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "user_reset_password_requests")
public class UserResetPasswordRequestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pid;
    
    private String code;
    private Boolean isUsed;
    private LocalDateTime createdAt;
    
    @ManyToOne
    @JoinColumn(name = "user_pid")
    private UserEntity userEntity;
}
