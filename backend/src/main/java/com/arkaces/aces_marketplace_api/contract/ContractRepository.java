package com.arkaces.aces_marketplace_api.contract;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ContractRepository extends JpaRepository<ContractEntity, Long> {
    
    @Query(
        "select c, a, s from ContractEntity c " +
        "join fetch c.accountEntity a " +
        "join fetch c.serviceEntity s " +
        "where a.pid = :accountPid and c.id = :contractId"
    )
    ContractEntity findOne(@Param("accountPid") Long accountPid, @Param("contractId") String contractId);
}
