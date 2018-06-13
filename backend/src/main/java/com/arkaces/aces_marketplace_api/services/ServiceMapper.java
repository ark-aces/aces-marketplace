package com.arkaces.aces_marketplace_api.services;

import com.arkaces.aces_marketplace_api.service_category.ServiceCategory;
import com.arkaces.aces_marketplace_api.service_client.Capacity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ServiceMapper {

    private final ModelMapper modelMapper;

    public Service map(ServiceEntity serviceEntity) {
        Service service = modelMapper.map(serviceEntity, Service.class);
        service.setCapacities(serviceEntity.getServiceCapacityEntities().stream()
            .map(x -> modelMapper.map(x, Capacity.class))
            .collect(Collectors.toList()));
        service.setServiceCategories(serviceEntity.getServiceCategoryLinkEntities().stream()
            .map(x -> {
                ServiceCategory serviceCategory = modelMapper.map(x.getServiceCategoryEntity(), ServiceCategory.class);
                serviceCategory.setId(x.getServiceCategoryEntity().getPid());
                return serviceCategory;
            })
            .collect(Collectors.toList()));
        return service;
    }

}
