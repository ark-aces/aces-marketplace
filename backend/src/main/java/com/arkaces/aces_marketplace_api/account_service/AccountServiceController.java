package com.arkaces.aces_marketplace_api.account_service;

import com.arkaces.aces_marketplace_api.account.AccountEntity;
import com.arkaces.aces_marketplace_api.account.AccountRepository;
import com.arkaces.aces_marketplace_api.common.IdentifierGenerator;
import com.arkaces.aces_marketplace_api.error.ErrorCodes;
import com.arkaces.aces_marketplace_api.error.ValidationException;
import com.arkaces.aces_marketplace_api.security.AuthenticatedUser;
import com.arkaces.aces_marketplace_api.service_client.Capacity;
import com.arkaces.aces_marketplace_api.service_client.ServiceClient;
import com.arkaces.aces_marketplace_api.service_client.ServiceResponse;
import com.arkaces.aces_marketplace_api.services.Service;
import com.arkaces.aces_marketplace_api.services.ServiceCapacityEntity;
import com.arkaces.aces_marketplace_api.services.ServiceEntity;
import com.arkaces.aces_marketplace_api.services.ServiceRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AccountServiceController {
    
    private final AccountRepository accountRepository;
    private final ServiceRepository serviceRepository;
    private final ServiceClient serviceClient;
    private final ModelMapper modelMapper;
    private final IdentifierGenerator identifierGenerator;
    
    @PostMapping("/account/services")
    public AccountService createService(
        @AuthenticationPrincipal AuthenticatedUser authenticatedUser, 
        @RequestBody CreateServiceRequest createServiceRequest
    ) {
        AccountEntity accountEntity = accountRepository.findOne(authenticatedUser.getAccountPid());

        ServiceResponse serviceResponse;
        try {
            serviceResponse = serviceClient.getServiceInfo(createServiceRequest.getUrl());
        } catch (Exception e) {
            throw new ValidationException(ErrorCodes.SERVICE_INFO_REQUEST_FAILED, "Failed to get Service Info from the given URL", e);
        }
        
        String id = identifierGenerator.generate();
        
        ServiceEntity serviceEntity = new ServiceEntity();
        serviceEntity.setId(id);
        serviceEntity.setUrl(createServiceRequest.getUrl());
        serviceEntity.setCreatedAt(LocalDateTime.now());
        serviceEntity.setName(serviceResponse.getName());
        serviceEntity.setDescription(serviceResponse.getDescription());
        serviceEntity.setVersion(serviceResponse.getVersion());
        serviceEntity.setWebsiteUrl(serviceResponse.getWebsiteUrl());
        serviceEntity.setAccountEntity(accountEntity);
        serviceEntity.setIsTestnet(serviceResponse.getIsTestNet());

        serviceEntity.setFlatFee(new BigDecimal(serviceResponse.getFlatFee()));
        serviceEntity.setPercentFee(new BigDecimal(serviceResponse.getPercentFee().replace("%", "")));

        List<ServiceCapacityEntity> serviceCapacityEntityList = new ArrayList<>();
        for (Capacity capacity : serviceResponse.getCapacities()) {
            ServiceCapacityEntity serviceCapacityEntity = new ServiceCapacityEntity();
            serviceCapacityEntity.setServiceEntity(serviceEntity);
            serviceCapacityEntity.setUnit(capacity.getUnit());
            serviceCapacityEntity.setValue(capacity.getValue());
        }
        serviceEntity.setServiceCapacityEntities(serviceCapacityEntityList);

        serviceRepository.save(serviceEntity);
        
        Service service = modelMapper.map(serviceEntity, Service.class);
        
        AccountService accountService = new AccountService();
        accountService.setService(service);
        accountService.setCreatedAt(LocalDateTime.now().atOffset(ZoneOffset.UTC).toString());
        
        return accountService;
    }

}
