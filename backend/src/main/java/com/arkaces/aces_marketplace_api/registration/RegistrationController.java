package com.arkaces.aces_marketplace_api.registration;

import lombok.RequiredArgsConstructor;
import org.apache.tomcat.jni.Local;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RegistrationController {

    private RegistrationRepository registrationRepository;
    private ModelMapper modelMapper;
    
    @PostMapping("registrations")
    public Registration post(CreateRegistrationRequest createRegistrationRequest) {
        RegistrationEntity registrationEntity = new RegistrationEntity();
        registrationEntity.setContactEmailAddress(createRegistrationRequest.getContactEmailAddress());
        registrationEntity.setCreatedAt(LocalDateTime.now());
        registrationEntity.setIsEmailAddressVerified(false);
        registrationEntity.setEmailAddressVerificationCode(emailAddressVerificationCode);
        registrationRepository.save(registrationEntity);
        
        
        // todo: send registration email
        
        Registration registration = modelMapper.map(registrationEntity, Registration.class);
        
    }

}
