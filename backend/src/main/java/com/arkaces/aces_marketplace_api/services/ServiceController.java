package com.arkaces.aces_marketplace_api.services;

import com.arkaces.aces_marketplace_api.PageViewMapper;
import com.arkaces.aces_marketplace_api.common.PageView;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ServiceController {

    private final ServiceRepository serviceRepository;
    private final ModelMapper modelMapper;
    private final PageViewMapper pageViewMapper;
    
    @GetMapping("/services")
    public PageView<Service> getServices(Pageable pageable) {
        int pageSize = 20;
        PageRequest queryPageRequest = new PageRequest(pageable.getPageNumber(), pageSize);
        Page<Service> page = serviceRepository.findAll(queryPageRequest)
            .map(serviceEntity -> modelMapper.map(serviceEntity, Service.class));
        return pageViewMapper.map(page, Service.class);
    }

}
