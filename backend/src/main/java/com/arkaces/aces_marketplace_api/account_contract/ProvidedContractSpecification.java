package com.arkaces.aces_marketplace_api.account_contract;

import com.arkaces.aces_marketplace_api.account.AccountEntity;
import com.arkaces.aces_marketplace_api.contract.ContractEntity;
import com.arkaces.aces_marketplace_api.services.ServiceEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class ProvidedContractSpecification implements Specification<ContractEntity> {

    private final ProvidedContractSpecificationCriteria criteria;
    
    @Override
    public Predicate toPredicate(Root<ContractEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        Join<ContractEntity, ServiceEntity> serviceEntityJoin = root.join("serviceEntity", JoinType.INNER);
        Join<ServiceEntity, AccountEntity> accountEntityJoin = serviceEntityJoin.join("accountEntity", JoinType.INNER);

        if (! StringUtils.isEmpty(criteria.getStatus())) {
            if (criteria.getStatus().startsWith("pending")) {
                predicates.add(cb.like(root.get("status"), "pending%"));
            } else {
                predicates.add(cb.equal(root.get("status"), criteria.getStatus()));
            }
        }

        predicates.add(accountEntityJoin.get("pid").in(criteria.getAccountEntity().getPid()));

        Predicate[] predicatesArray = new Predicate[predicates.size()];
        predicates.toArray(predicatesArray);
        
        return cb.and(predicatesArray);
    }
}
