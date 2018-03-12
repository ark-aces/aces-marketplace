package com.arkaces.aces_marketplace_api.registration;

import com.arkaces.aces_marketplace_api.email_verification.EmailVerificationService;
import com.arkaces.aces_marketplace_api.user.UserEntity;
import com.arkaces.aces_marketplace_api.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class RegistrationCreatedListener {

    private final UserRepository userRepository;
    private final EmailVerificationService emailVerificationService;
    
    @EventListener
    public void handleOrderCreatedEvent(UserCreatedEvent userCreatedEvent) {
        UserEntity userEntity = userRepository.findOne(userCreatedEvent.getUserPid());
        try {
            emailVerificationService.sendVerificationEmail(
                userEntity.getName(),
                userEntity.getEmailAddress(),
                userEntity.getEmailAddressVerificationCode()
            );
            log.info("Sent verification email to " + userEntity.getEmailAddress());
        } catch (Exception e) {
            log.error("Failed to send email verification email", e);
        }
    }

}
