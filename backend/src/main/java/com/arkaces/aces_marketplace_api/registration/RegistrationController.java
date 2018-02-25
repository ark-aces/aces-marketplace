package com.arkaces.aces_marketplace_api.registration;

import com.arkaces.aces_marketplace_api.account.AccountEntity;
import com.arkaces.aces_marketplace_api.account.ApiKeyEntity;
import com.arkaces.aces_marketplace_api.common.CodeGenerator;
import com.arkaces.aces_marketplace_api.common.IdentifierGenerator;
import com.arkaces.aces_marketplace_api.user.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Arrays;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RegistrationController {

    private final RegistrationRepository registrationRepository;
    private final RegistrationMapper registrationMapper;
    private final CodeGenerator codeGenerator;
    private final IdentifierGenerator identifierGenerator;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    
    @PostMapping("registrations")
    public Registration post(@RequestBody CreateRegistrationRequest createRegistrationRequest) {
        
        String emailAddressVerificationCode = codeGenerator.generate(20);
        
        RegistrationEntity registrationEntity = new RegistrationEntity();
        registrationEntity.setId(identifierGenerator.generate());
        registrationEntity.setCreatedAt(LocalDateTime.now());
        
        // todo: send registration email

        String passwordHash = bCryptPasswordEncoder.encode(createRegistrationRequest.getUserPassword());

        UserEntity userEntity = new UserEntity();
        userEntity.setId(identifierGenerator.generate());
        userEntity.setName(createRegistrationRequest.getUserName());
        userEntity.setEmailAddress(createRegistrationRequest.getUserEmailAddress());
        userEntity.setIsEmailAddressVerified(false);
        userEntity.setPasswordHash(passwordHash);
        
        ApiKeyEntity apiKeyEntity = new ApiKeyEntity();
        apiKeyEntity.setId(identifierGenerator.generate());
        apiKeyEntity.setKey(codeGenerator.generate(40));
        
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setContactEmailAddress(createRegistrationRequest.getContactEmailAddress());
        accountEntity.setEmailAddressVerificationCode(emailAddressVerificationCode);
        accountEntity.setIsContactEmailAddressVerified(false);
        accountEntity.setId(identifierGenerator.generate());
        accountEntity.setUserEntities(Arrays.asList(userEntity));
        accountEntity.setApiKeyEntities(Arrays.asList(apiKeyEntity));
        
        registrationEntity.setAccountEntity(accountEntity);

        registrationRepository.save(registrationEntity);

        return registrationMapper.map(registrationEntity);
    }

}
