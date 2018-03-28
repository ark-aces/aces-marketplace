package com.arkaces.aces_marketplace_api.account_contract;

import com.arkaces.aces_marketplace_api.account.AccountEntity;
import com.arkaces.aces_marketplace_api.contract.ContractEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class ContractSpecification implements Specification<ContractEntity> {

    private final ContractSpecificationCriteria criteria;
    
    @Override
    public Predicate toPredicate(Root<ContractEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        Join<ContractEntity, AccountEntity> accountEntityJoin = root.join("accountEntity", JoinType.INNER);

        if (! StringUtils.isEmpty(criteria.getStatus())) {
            predicates.add(cb.equal(root.get("status"), criteria.getStatus()));
        }

        if (criteria.getAccountEntity() != null) {
            predicates.add(accountEntityJoin.get("pid").in(criteria.getAccountEntity().getPid()));
        }

        Predicate[] predicatesArray = new Predicate[predicates.size()];
        predicates.toArray(predicatesArray);
        
        return cb.and(predicatesArray);
    }
}
