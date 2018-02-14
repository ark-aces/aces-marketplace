package com.arkaces.aces_marketplace_api.service_category;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ServiceCategoryInitializer {
    
    private final ServiceCategoryRepository serviceCategoryRepository;
    
    @PostConstruct
    public void initCategories() {
        List<ServiceCategoryEntity> items = serviceCategoryRepository.findAll();
        if (items.size() == 0) {
            addInitialCategories();
        }
    }
    
    private void addInitialCategories() {
        List<String> categories = Arrays.asList(
            "Computing",
            "Storage",
            "Privacy",
            "Smart Contracts",
            "Registrars",
            "Transfer Channels",
            "Security",
            "Identity",
            "Internet of Things",
            "Networking",
            "Donations"
        );
        for (int i = 0; i < categories.size(); i++) {
            ServiceCategoryEntity serviceCategoryEntity = new ServiceCategoryEntity();
            serviceCategoryEntity.setName(categories.get(i));
            serviceCategoryEntity.setPosition(i);
            serviceCategoryRepository.save(serviceCategoryEntity);
        }
    }
}
