package com.arkaces.aces_marketplace_api.email_verification;

import com.arkaces.aces_marketplace_api.user.UserEntity;
import com.arkaces.aces_marketplace_api.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EmailVerificationController {
    
    private final UserRepository userRepository;
    
    @PostMapping("/emailVerifications")
    @ResponseStatus(value = HttpStatus.OK)
    public Verification createEmailVerification(@RequestBody EmailVerificationRequest emailVerificationRequest) {
        String code = emailVerificationRequest.getCode();
        List<UserEntity> userEntities = userRepository.findAllByEmailAddressVerificationCode(code);
        for (UserEntity userEntity : userEntities) {
            userEntity.setIsEmailAddressVerified(true);
            userRepository.save(userEntity);
        }
        
        Verification verification = new Verification();
        verification.setVerifiedAt(LocalDateTime.now().atOffset(ZoneOffset.UTC).toString());
        
        return verification;
    }
}
