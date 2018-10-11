package com.arkaces.aces_marketplace_api.account_service;

import com.arkaces.aces_marketplace_api.account.AccountEntity;
import com.arkaces.aces_marketplace_api.account.AccountRepository;
import com.arkaces.aces_marketplace_api.common.IdentifierGenerator;
import com.arkaces.aces_marketplace_api.common.PageView;
import com.arkaces.aces_marketplace_api.common.PageViewMapper;
import com.arkaces.aces_marketplace_api.error.ErrorCodes;
import com.arkaces.aces_marketplace_api.error.NotFoundException;
import com.arkaces.aces_marketplace_api.error.ValidationException;
import com.arkaces.aces_marketplace_api.security.AuthenticatedUser;
import com.arkaces.aces_marketplace_api.service_category.ServiceCategoryEntity;
import com.arkaces.aces_marketplace_api.service_category.ServiceCategoryRepository;
import com.arkaces.aces_marketplace_api.service_client.Capacity;
import com.arkaces.aces_marketplace_api.service_client.ServiceClient;
import com.arkaces.aces_marketplace_api.service_client.ServiceResponse;
import com.arkaces.aces_marketplace_api.services.*;
import com.google.common.collect.Sets;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AccountServiceController {
    
    private final AccountRepository accountRepository;
    private final ServiceRepository serviceRepository;
    private final ModelMapper modelMapper;
    private final IdentifierGenerator identifierGenerator;
    private final ServiceMapper serviceMapper;
    private final PageViewMapper pageViewMapper;
    
    private final ServiceCategoryRepository serviceCategoryRepository;
    private final CreateServiceRequestValidator createServiceRequestValidator;
    private final UpdateServiceRequestValidator updateServiceRequestValidator;

    private final AccountServiceService accountServiceService;
    
    @Transactional
    @PostMapping("/account/services")
    public Service createService(
        @AuthenticationPrincipal AuthenticatedUser authenticatedUser,
        @RequestBody CreateServiceRequest createServiceRequest
    ) {
        AccountEntity accountEntity = accountRepository.findOne(authenticatedUser.getAccountPid());
        
        createServiceRequestValidator.validate(createServiceRequest);
        
        String id = identifierGenerator.generate();
        
        ServiceEntity serviceEntity = new ServiceEntity();
        serviceEntity.setLabel(createServiceRequest.getLabel());
        serviceEntity.setId(id);
        serviceEntity.setCreatedAt(LocalDateTime.now());
        serviceEntity.setAccountEntity(accountEntity);
        serviceEntity.setStatus(ServiceStatus.INACTIVE);

        if (createServiceRequest.getIsTestnet() != null) {
            serviceEntity.setIsTestnet(createServiceRequest.getIsTestnet());
        } else {
            serviceEntity.setIsTestnet(false);
        }

        serviceEntity.setUrl(createServiceRequest.getUrl());
        setServiceCategoryLinkEntities(serviceEntity, createServiceRequest.getCategoryPids());

        serviceEntity = serviceRepository.save(serviceEntity);

        accountServiceService.syncToRemote(serviceEntity.getId(), createServiceRequest.getUrl());

        return modelMapper.map(serviceEntity, Service.class);
    }

    @GetMapping("/account/services/{serviceId}")
    public Service getService(
        @AuthenticationPrincipal AuthenticatedUser authenticatedUser,
        @PathVariable String serviceId
    ) {
        ServiceEntity serviceEntity = getServiceOrThrowNotFound(authenticatedUser, serviceId);
        
        return serviceMapper.map(serviceEntity);
    }
    
    @Transactional
    @PostMapping("/account/services/{serviceId}")
    public Service updateService(
        @AuthenticationPrincipal AuthenticatedUser authenticatedUser,
        @PathVariable String serviceId,
        @RequestBody UpdateServiceRequest updateServiceRequest
    ) {
        ServiceEntity serviceEntity = getServiceOrThrowNotFound(authenticatedUser, serviceId);

        updateServiceRequestValidator.validate(updateServiceRequest);

        if (updateServiceRequest.getUrl() != null) {
            serviceEntity.setUrl(updateServiceRequest.getUrl());
            accountServiceService.syncToRemote(serviceEntity.getId(), updateServiceRequest.getUrl());
        }
        
        if (updateServiceRequest.getLabel() != null) {
            serviceEntity.setLabel(updateServiceRequest.getLabel());
        }
        
        if (updateServiceRequest.getIsTestnet() != null) {
            serviceEntity.setIsTestnet(updateServiceRequest.getIsTestnet());
        }
        
        if (updateServiceRequest.getCategoryPids() != null) {
            setServiceCategoryLinkEntities(serviceEntity, updateServiceRequest.getCategoryPids());
        }
        
        if (updateServiceRequest.getStatus() != null) {
            serviceEntity.setStatus(updateServiceRequest.getStatus());
        }

        serviceEntity = serviceRepository.save(serviceEntity);

        return serviceMapper.map(serviceEntity);
    }

    @GetMapping("/account/services")
    public PageView<Service> listServices(
        @AuthenticationPrincipal AuthenticatedUser authenticatedUser,
        Pageable pageable
    ) {
        AccountEntity accountEntity = accountRepository.findOne(authenticatedUser.getAccountPid());

        PageRequest pageRequest = new PageRequest(0, 20, new Sort(Sort.Direction.DESC, "createdAt"));
        Page<Service> servicePage = serviceRepository.findAllByAccountEntityPid(accountEntity.getPid(), pageRequest)
            .map(serviceMapper::map);
        
        return pageViewMapper.map(servicePage, Service.class);
    }

    private ServiceEntity getServiceOrThrowNotFound(AuthenticatedUser authenticatedUser, String serviceId) {
        Specification<ServiceEntity> serviceEntitySpecification = (root, criteriaQuery, criteriaBuilder) -> {
            Join<ServiceEntity, AccountEntity> accountEntityJoin = root.join("accountEntity");
            return criteriaBuilder.and(
                criteriaBuilder.equal(accountEntityJoin.get("pid"), authenticatedUser.getAccountPid()),
                criteriaBuilder.equal(root.get("id"), serviceId)
            );
        };
        ServiceEntity serviceEntity = serviceRepository.findOne(serviceEntitySpecification);
        if (serviceEntity == null) {
            throw new NotFoundException(ErrorCodes.NOT_FOUND, "Service not found");
        }

        return serviceEntity;
    }

    private void setServiceCategoryLinkEntities(ServiceEntity serviceEntity, Set<Long> categoryPids) {
        Set<Long> newCategoryPids = new HashSet<>(categoryPids);
        Set<Long> existingCategoryPids = new HashSet<>();
        if (serviceEntity.getServiceCategoryLinkEntities() != null) {
            existingCategoryPids = serviceEntity.getServiceCategoryLinkEntities().stream()
                .map(x -> x.getServiceCategoryEntity().getPid())
                .collect(Collectors.toSet());
        }
        
        Set<Long> toRemoveCategoryIds = new HashSet<>(existingCategoryPids);
        toRemoveCategoryIds.removeAll(newCategoryPids);
        
        Set<Long> toAddCategoryIds = new HashSet<>(categoryPids);
        toAddCategoryIds.removeAll(existingCategoryPids);

        Iterator<ServiceCategoryLinkEntity> it = serviceEntity.getServiceCategoryLinkEntities().iterator();
        while (it.hasNext()) {
            ServiceCategoryLinkEntity serviceCategoryLinkEntity = it.next();
            if (toRemoveCategoryIds.contains(serviceCategoryLinkEntity.getServiceCategoryEntity().getPid())) {
                serviceCategoryLinkEntity.setServiceEntity(null);
                it.remove();
            }
        }
        
        for (ServiceCategoryEntity serviceCategoryEntity : serviceCategoryRepository.findAll(toAddCategoryIds)) {
            ServiceCategoryLinkEntity serviceCategoryLinkEntity = new ServiceCategoryLinkEntity();
            serviceCategoryLinkEntity.setServiceCategoryEntity(serviceCategoryEntity);
            serviceCategoryLinkEntity.setServiceEntity(serviceEntity);
            serviceEntity.getServiceCategoryLinkEntities().add(serviceCategoryLinkEntity);
        }
    }

}
