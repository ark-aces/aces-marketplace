package com.arkaces.aces_marketplace_api.service_category;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ServiceCategoryController {
    
    private final ServiceCategoryRepository serviceCategoryRepository;
    private final ModelMapper modelMapper;
    
    @GetMapping("serviceCategories")
    public List<ServiceCategory> listAllSortedByOrder() {
        Sort sort = new Sort(new Sort.Order(Sort.Direction.ASC, "position"));
        List<ServiceCategoryEntity> categoryEntityList = serviceCategoryRepository.findAll(sort);
        return categoryEntityList.stream().map(x -> {
            ServiceCategory serviceCategory = modelMapper.map(x, ServiceCategory.class);
            serviceCategory.setId(x.getPid());
            return serviceCategory;
        })
        .collect(Collectors.toList());
    }
}
