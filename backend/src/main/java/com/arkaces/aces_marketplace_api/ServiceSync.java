package com.arkaces.aces_marketplace_api;

import com.arkaces.aces_marketplace_api.account_service.AccountServiceService;
import com.arkaces.aces_marketplace_api.services.ServiceEntity;
import com.arkaces.aces_marketplace_api.services.ServiceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ServiceSync {

    private final ServiceRepository serviceRepository;
    private final AccountServiceService accountServiceService;

    @Scheduled(fixedDelayString = "${syncIntervalSec}000")
    public void sync() {
        log.info("Executing sync");
        try {
            List<ServiceEntity> serviceEntities = serviceRepository.findAll();
            for (ServiceEntity serviceEntity : serviceEntities) {
                try {
                    log.info("Syncing service " + serviceEntity.getId() + " to url " + serviceEntity.getUrl());
                    accountServiceService.syncToRemote(serviceEntity.getId(), serviceEntity.getUrl());
                } catch (Exception e) {
                    log.error("Failed to sync service " + serviceEntity.getId(), e);
                }
            }
        } catch (Exception e) {
            log.error("Sync failed", e);
        }
    }

}
