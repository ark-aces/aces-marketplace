package com.arkaces.aces_marketplace_api.contract;

import com.arkaces.aces_marketplace_api.service_client.RemoteContractResponse;
import org.springframework.stereotype.Service;

import java.time.ZoneOffset;

@Service
public class ContractMapper {
    
    public Contract map(ContractEntity contractEntity, RemoteContractResponse remoteContractResponse) {
        Contract contract = new Contract();
        contract.setId(contractEntity.getId());
        contract.setServiceId(contractEntity.getServiceEntity().getId());
        contract.setCreatedAt(contractEntity.getCreatedAt().atOffset(ZoneOffset.UTC).toString());
        contract.setStatus(contractEntity.getStatus());
        contract.setResults(remoteContractResponse.getResults());
        
        return contract;
    }
}
