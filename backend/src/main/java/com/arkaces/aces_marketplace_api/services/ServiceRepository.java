package com.arkaces.aces_marketplace_api.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ServiceRepository extends JpaRepository<ServiceEntity, Long>, JpaSpecificationExecutor<ServiceEntity> {

    ServiceEntity findOneById(String id);
    
    Page<ServiceEntity> findAllByAccountEntityPid(Long accountEntityPid, Pageable pageable);
}
