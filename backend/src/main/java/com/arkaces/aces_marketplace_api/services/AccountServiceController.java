package com.arkaces.aces_marketplace_api.services;

import com.arkaces.aces_marketplace_api.account.AccountEntity;
import com.arkaces.aces_marketplace_api.account.AccountRepository;
import com.arkaces.aces_marketplace_api.security.AuthenticatedUser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AccountServiceController {
    
    private final AccountRepository accountRepository;
    
    @PostMapping("/account/services")
    public Service createService(
        @AuthenticationPrincipal AuthenticatedUser authenticatedUser, 
        @RequestBody CreateServiceRequest createServiceRequest
    ) {
        AccountEntity accountEntity = accountRepository.findOne(authenticatedUser.getAccountPid());
        
        throw new RuntimeException("not implemented!");
    }

}
