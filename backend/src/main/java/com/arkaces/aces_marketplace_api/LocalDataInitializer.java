package com.arkaces.aces_marketplace_api;

import com.arkaces.aces_marketplace_api.common.IdentifierGenerator;
import com.arkaces.aces_marketplace_api.registration.CreateRegistrationRequest;
import com.arkaces.aces_marketplace_api.registration.RegistrationEntity;
import com.arkaces.aces_marketplace_api.registration.RegistrationRepository;
import com.arkaces.aces_marketplace_api.registration.RegistrationService;
import com.arkaces.aces_marketplace_api.services.ServiceCapacityEntity;
import com.arkaces.aces_marketplace_api.services.ServiceEntity;
import com.arkaces.aces_marketplace_api.services.ServiceRepository;
import com.arkaces.aces_marketplace_api.services.ServiceStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;

@Profile("dev")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Component
public class LocalDataInitializer implements ApplicationRunner{

    private final RegistrationService registrationService;
    private final RegistrationRepository registrationRepository;
    private final IdentifierGenerator identifierGenerator;
    private final ServiceRepository serviceRepository;

    @Override
    public void run(ApplicationArguments applicationArguments) {
        if (serviceRepository.findAll().isEmpty()) {
            CreateRegistrationRequest createRegistrationRequest = new CreateRegistrationRequest();
            createRegistrationRequest.setUserEmailAddress("User1@test.com");
            createRegistrationRequest.setUserPassword("password");
            RegistrationEntity registrationEntity = registrationService.createRegistration(createRegistrationRequest);
            registrationRepository.save(registrationEntity);

            for (int i = 0; i < 5; i++) {
                String id = identifierGenerator.generate();

                ServiceEntity serviceEntity = new ServiceEntity();
                serviceEntity.setId(id);
                serviceEntity.setLabel("Service Label" + i);
                serviceEntity.setStatus(ServiceStatus.ACTIVE);
                serviceEntity.setUrl("http://localhost:9999/service-" + i);
                serviceEntity.setCreatedAt(LocalDateTime.now());
                serviceEntity.setName("Service " + i);
                serviceEntity.setDescription("Service description " + i);
                serviceEntity.setVersion("1.0.0");
                serviceEntity.setWebsiteUrl("https://arkaces.com");
                serviceEntity.setAccountEntity(registrationEntity.getAccountEntity());
                serviceEntity.setIsTestnet(i % 2 == 0);
                serviceEntity.setFlatFee(new BigDecimal("1.00"));
                serviceEntity.setFlatFeeUnit("ARK");
                serviceEntity.setPercentFee(new BigDecimal("2.25"));

                ServiceCapacityEntity serviceCapacityEntity = new ServiceCapacityEntity();
                serviceCapacityEntity.setServiceEntity(serviceEntity);
                serviceCapacityEntity.setUnit("ARK");
                serviceCapacityEntity.setValue(new BigDecimal("5000"));

                serviceEntity.setServiceCapacityEntities(Collections.singletonList(serviceCapacityEntity));

                serviceRepository.save(serviceEntity);
            }
        }
    }
}
