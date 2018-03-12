package com.arkaces.aces_marketplace_api.registration;

import com.arkaces.aces_marketplace_api.account.AccountEntity;
import com.arkaces.aces_marketplace_api.account.AccountRepository;
import com.arkaces.aces_marketplace_api.common.CodeGenerator;
import com.arkaces.aces_marketplace_api.common.IdentifierGenerator;
import com.arkaces.aces_marketplace_api.user.UserEntity;
import com.arkaces.aces_marketplace_api.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;

@Service
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RegistrationService {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final RegistrationRepository registrationRepository;
    private final CodeGenerator codeGenerator;
    private final IdentifierGenerator identifierGenerator;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    
    public RegistrationEntity createRegistration(CreateRegistrationRequest createRegistrationRequest) {
        String emailAddressVerificationCode = codeGenerator.generate(20);

        String passwordHash = bCryptPasswordEncoder.encode(createRegistrationRequest.getUserPassword());

        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setContactEmailAddress(createRegistrationRequest.getContactEmailAddress());
        accountEntity.setEmailAddressVerificationCode(emailAddressVerificationCode);
        accountEntity.setIsContactEmailAddressVerified(false);
        accountEntity.setCreatedAt(LocalDateTime.now());
        accountEntity.setId(identifierGenerator.generate());
        accountRepository.save(accountEntity);

        String userEmailAddressVerificationCode = codeGenerator.generate(20);

        UserEntity userEntity = new UserEntity();
        userEntity.setId(identifierGenerator.generate());
        userEntity.setName(createRegistrationRequest.getUserName());
        userEntity.setEmailAddress(createRegistrationRequest.getUserEmailAddress());
        userEntity.setEmailAddressVerificationCode(userEmailAddressVerificationCode);
        userEntity.setIsEmailAddressVerified(false);
        userEntity.setPasswordHash(passwordHash);
        userEntity.setCreatedAt(LocalDateTime.now());
        userEntity.setAccountEntity(accountEntity);
        userRepository.save(userEntity);

        accountEntity.setUserEntities(Arrays.asList(userEntity));

        RegistrationEntity registrationEntity = new RegistrationEntity();
        registrationEntity.setId(identifierGenerator.generate());
        registrationEntity.setCreatedAt(LocalDateTime.now());
        registrationEntity.setAccountEntity(accountEntity);
        registrationRepository.save(registrationEntity);

        accountEntity.setRegistrationEntity(registrationEntity);
        
        return registrationEntity;
    }
    
}
