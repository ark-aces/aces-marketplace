package com.arkaces.aces_marketplace_api.services;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<ServiceEntity, Long> {
    ServiceEntity findOneById(String id);
}
