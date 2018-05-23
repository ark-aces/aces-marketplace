package com.arkaces.aces_marketplace_api.services;

import com.arkaces.aces_marketplace_api.common.PageView;
import com.arkaces.aces_marketplace_api.common.PageViewMapper;
import com.arkaces.aces_marketplace_api.error.ErrorCodes;
import com.arkaces.aces_marketplace_api.error.NotFoundException;
import com.arkaces.aces_marketplace_api.service_client.Capacity;
import com.arkaces.aces_marketplace_api.service_client.ServiceClient;
import com.arkaces.aces_marketplace_api.service_client.ServiceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ServiceController {

    private final ServiceRepository serviceRepository;
    private final ServiceMapper serviceMapper;
    private final PageViewMapper pageViewMapper;
    private final ServiceClient serviceClient;

    @GetMapping("/services")
    public PageView<Service> getServices(ServiceSearchCriteria searchCriteria, @PageableDefault(size = 20) Pageable pageable) {
        ServiceSpecification specification = new ServiceSpecification(searchCriteria);
        Page<Service> servicePage = serviceRepository.findAll(specification, pageable).map(serviceMapper::map);
        return pageViewMapper.map(servicePage, Service.class);
    }

    @GetMapping("/services/{id}")
    public Service getService(@PathVariable String id) {
        ServiceEntity serviceEntity = serviceRepository.findOneById(id);
        if (serviceEntity == null) {
            throw new NotFoundException(ErrorCodes.NOT_FOUND, "Service not found.");
        }
        return serviceMapper.map(serviceEntity);
    }

    @GetMapping("/services/{id}/info")
    public ServiceResponse getServiceInfo(@PathVariable String id) {
        ServiceEntity serviceEntity = serviceRepository.findOneById(id);
        
        if (serviceEntity == null) {
            throw new NotFoundException(ErrorCodes.NOT_FOUND, "Service not found.");
        }
        String baseUrl = serviceEntity.getUrl();
        ServiceResponse serviceResponse = serviceClient.getServiceInfo(baseUrl);
        
        // update capacity value
        for (ServiceCapacityEntity serviceCapacityEntity : serviceEntity.getServiceCapacityEntities()) {
            if (serviceResponse.getCapacities() != null) {
                for (Capacity capacity : serviceResponse.getCapacities()) {
                    if (serviceCapacityEntity.getUnit().equals(capacity.getUnit())) {
                        serviceCapacityEntity.setValue(capacity.getValue());
                    }
                }
            }
        }
        serviceRepository.save(serviceEntity);
        
        return serviceResponse;
    }
}
