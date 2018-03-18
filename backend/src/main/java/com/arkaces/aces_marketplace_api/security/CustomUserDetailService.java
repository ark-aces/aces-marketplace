package com.arkaces.aces_marketplace_api.security;

import com.arkaces.aces_marketplace_api.user.UserEntity;
import com.arkaces.aces_marketplace_api.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findOneByEmailAddress(s);

        List<String> roles = new ArrayList<>();
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (String role : roles) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role));
        }
        
        return new AuthenticatedUser(
            userEntity.getId(),
            userEntity.getPasswordHash(),
            grantedAuthorities,
            userEntity.getAccountEntity().getPid(),
            userEntity.getPid()
        );
    }
    
}