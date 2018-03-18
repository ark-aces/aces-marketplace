package com.arkaces.aces_marketplace_api.security;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Data
@EqualsAndHashCode(callSuper = true)
public class AuthenticatedUser extends User {

    private Long userPid;
    private Long accountPid;
    
    public AuthenticatedUser(
        String emailAddress, 
        String password, Collection<? extends GrantedAuthority> authorities, 
        Long accountPid,
        Long userPid
    ) {
        super(emailAddress, password, authorities);
        this.accountPid = accountPid;
        this.userPid = userPid;
    }
    
}
