package com.arkaces.aces_marketplace_api.account_service;

import com.arkaces.aces_marketplace_api.error.ErrorCodes;
import com.arkaces.aces_marketplace_api.error.ValidationException;
import com.arkaces.aces_marketplace_api.service_client.Capacity;
import com.arkaces.aces_marketplace_api.service_client.ServiceClient;
import com.arkaces.aces_marketplace_api.service_client.ServiceResponse;
import com.arkaces.aces_marketplace_api.services.ServiceCapacityEntity;
import com.arkaces.aces_marketplace_api.services.ServiceEntity;
import com.arkaces.aces_marketplace_api.services.ServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Iterator;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Transactional
public class AccountServiceService {

    private final ServiceClient serviceClient;
    private final ServiceRepository serviceRepository;

    public void syncToRemote(String id, String serviceUrl) {
        ServiceResponse serviceResponse;
        try {
            serviceResponse = serviceClient.getServiceInfo(serviceUrl);
        } catch (Exception e) {
            throw new ValidationException(ErrorCodes.SERVICE_INFO_REQUEST_FAILED, "Failed to get Service Info from the given URL", e);
        }

        ServiceEntity serviceEntity = serviceRepository.findOneById(id);

        serviceEntity.setName(serviceResponse.getName());
        serviceEntity.setDescription(serviceResponse.getDescription());
        serviceEntity.setVersion(serviceResponse.getVersion());
        serviceEntity.setWebsiteUrl(serviceResponse.getWebsiteUrl());
        serviceEntity.setUrl(serviceUrl);

        if (serviceResponse.getFlatFee() != null) {
            if (serviceResponse.getFlatFee().contains(" ")) {
                String[] parts = serviceResponse.getFlatFee().split(" ");
                serviceEntity.setFlatFee(new BigDecimal(parts[0]));
                serviceEntity.setFlatFeeUnit(parts[1]);
            } else {
                serviceEntity.setFlatFee(new BigDecimal(serviceResponse.getFlatFee()));
                serviceEntity.setFlatFeeUnit(serviceResponse.getFlatFeeUnit());
            }
        } else {
            serviceEntity.setFlatFee(BigDecimal.ZERO);
            serviceEntity.setFlatFeeUnit(null);
        }

        if (serviceResponse.getPercentFee() != null) {
            serviceEntity.setPercentFee(new BigDecimal(serviceResponse.getPercentFee().replace("%", "")));
        } else {
            serviceEntity.setPercentFee(BigDecimal.ZERO);
        }

        if (serviceEntity.getServiceCapacityEntities() != null) {
            Iterator<ServiceCapacityEntity> it = serviceEntity.getServiceCapacityEntities().iterator();
            while (it.hasNext()) {
                ServiceCapacityEntity serviceCapacityEntity = it.next();
                serviceCapacityEntity.setServiceEntity(null);
                it.remove();
            }
        }

        for (Capacity capacity : serviceResponse.getCapacities()) {
            ServiceCapacityEntity serviceCapacityEntity = new ServiceCapacityEntity();
            serviceCapacityEntity.setServiceEntity(serviceEntity);
            serviceCapacityEntity.setUnit(capacity.getUnit());
            serviceCapacityEntity.setValue(capacity.getValue());
            serviceEntity.getServiceCapacityEntities().add(serviceCapacityEntity);
        }

        serviceRepository.save(serviceEntity);
    }

}
