package com.arkaces.aces_marketplace_api.registration;

import com.arkaces.aces_marketplace_api.recaptcha.RecaptchaService;
import com.arkaces.aces_marketplace_api.user.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RegistrationController {

    private final RegistrationMapper registrationMapper;
    private final RegistrationService registrationService;
    private final UserCreatedService userCreatedService;
    private final CreateRegistrationRequestValidator createRegistrationRequestValidator;
    private final RecaptchaService recaptchaService;
    
    @PostMapping("registrations")
    public Registration post(@RequestBody CreateRegistrationRequest createRegistrationRequest, HttpServletRequest httpServletRequest) {
        String clientIp = httpServletRequest.getHeader("X-FORWARDED-FOR");
        if (clientIp == null || "".equals(clientIp)) {
            clientIp = httpServletRequest.getRemoteAddr();
        }
        createRegistrationRequestValidator.validate(createRegistrationRequest, clientIp);

        RegistrationEntity registrationEntity = registrationService.createRegistration(createRegistrationRequest);
        for (UserEntity userEntity : registrationEntity.getAccountEntity().getUserEntities()) {
            userCreatedService.notifyUserCreated(userEntity);
        }
        
        return registrationMapper.map(registrationEntity);
    }

}
