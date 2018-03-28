package com.arkaces.aces_marketplace_api.services;

import com.arkaces.aces_marketplace_api.common.PageViewMapper;
import com.arkaces.aces_marketplace_api.common.PageView;
import com.arkaces.aces_marketplace_api.error.ErrorCodes;
import com.arkaces.aces_marketplace_api.error.NotFoundException;
import com.arkaces.aces_marketplace_api.service_client.ServiceClient;
import com.arkaces.aces_marketplace_api.service_client.ServiceResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ServiceController {

    private final ServiceRepository serviceRepository;
    private final ModelMapper modelMapper;
    private final PageViewMapper pageViewMapper;
    private final ServiceClient serviceClient;
    
    @GetMapping("/services")
    public PageView<Service> getServices(Pageable pageable) {
        int pageSize = 20;
        PageRequest queryPageRequest = new PageRequest(pageable.getPageNumber(), pageSize);
        Page<Service> page = serviceRepository.findAll(queryPageRequest)
            .map(serviceEntity -> modelMapper.map(serviceEntity, Service.class));
        return pageViewMapper.map(page, Service.class);
    }

    @GetMapping("/services/{id}")
    public Service getService(@PathVariable String id) {
        ServiceEntity serviceEntity = serviceRepository.findOneById(id);
        if (serviceEntity == null) {
            throw new NotFoundException(ErrorCodes.NOT_FOUND, "Service not found.");
        }
        return modelMapper.map(serviceEntity, Service.class);
    }
    
    @GetMapping("/services/{id}/info")
    public ServiceResponse getServiceInfo(@PathVariable String id) {
        ServiceEntity serviceEntity = serviceRepository.findOneById(id);
        if (serviceEntity == null) {
            throw new NotFoundException(ErrorCodes.NOT_FOUND, "Service not found.");
        }
        
        String baseUrl = serviceEntity.getUrl();
        return serviceClient.getServiceInfo(baseUrl);
    }

}
