package com.arkaces.aces_marketplace_api.account_contract;

import com.arkaces.aces_marketplace_api.account.AccountEntity;
import com.arkaces.aces_marketplace_api.account.AccountRepository;
import com.arkaces.aces_marketplace_api.common.PageView;
import com.arkaces.aces_marketplace_api.common.PageViewMapper;
import com.arkaces.aces_marketplace_api.contract.*;
import com.arkaces.aces_marketplace_api.error.ErrorCodes;
import com.arkaces.aces_marketplace_api.error.NotFoundException;
import com.arkaces.aces_marketplace_api.error.ValidationException;
import com.arkaces.aces_marketplace_api.security.AuthenticatedUser;
import com.arkaces.aces_marketplace_api.service_client.RemoteContractResponse;
import com.arkaces.aces_marketplace_api.service_client.ServiceClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class AccountContractController {
    
    private final AccountRepository accountRepository;
    private final ContractRepository contractRepository;
    private final ModelMapper modelMapper;
    private final PageViewMapper pageViewMapper;
    private final ServiceClient serviceClient;
    private final ContractMapper contractMapper;

    @GetMapping("/account/contracts")
    public PageView<ContractListView> getContracts(
        @AuthenticationPrincipal AuthenticatedUser authenticatedUser,
        @RequestParam(required = false) String status, 
        Pageable pageable
    ) {
        AccountEntity accountEntity = accountRepository.findOne(authenticatedUser.getAccountPid());

        ContractSpecificationCriteria contractSpecificationCriteria = new ContractSpecificationCriteria();
        contractSpecificationCriteria.setAccountEntity(accountEntity);
        contractSpecificationCriteria.setStatus(status);
        ContractSpecification contractSpecification = new ContractSpecification(contractSpecificationCriteria);
        
        Page<ContractListView> page = contractRepository.findAll(contractSpecification, pageable)
            .map(contractEntity -> modelMapper.map(contractEntity, ContractListView.class));
        
        return pageViewMapper.map(page, ContractListView.class);
    }

    @GetMapping("/account/contracts/{contractId}")
    public Contract getContract(
        @AuthenticationPrincipal AuthenticatedUser authenticatedUser,
        @PathVariable String contractId
    ) {
        AccountEntity accountEntity = accountRepository.findOne(authenticatedUser.getAccountPid());
        
        ContractEntity contractEntity = contractRepository.findOne(accountEntity.getPid(), contractId);
        if (contractEntity == null) {
            throw new NotFoundException(ErrorCodes.NOT_FOUND, "Contract not found");
        }
        
        String serviceUrl = contractEntity.getServiceEntity().getUrl();
        String url = serviceUrl + "/contracts/" + contractEntity.getRemoteContractId();

        RemoteContractResponse remoteContractResponse;
        try {
            remoteContractResponse = serviceClient.getContract(url);
        } catch (Exception e) {
            throw new ValidationException(ErrorCodes.SERVICE_CONTRACT_REQUEST_FAILED, "Failed to get service contract", e);
        }
        
        // update status if changed
        if (! remoteContractResponse.getStatus().equals(contractEntity.getStatus())) {
            log.info("Updating contract status for contract id " + contractId + " from " 
                + contractEntity.getStatus() + " to " + remoteContractResponse.getStatus());
            contractEntity.setStatus(remoteContractResponse.getStatus());
            contractRepository.save(contractEntity);
        }

        return contractMapper.map(contractEntity, remoteContractResponse);
    }
}
