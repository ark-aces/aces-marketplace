package com.arkaces.aces_marketplace_api.services;

import com.arkaces.aces_marketplace_api.service_category.ServiceCategoryEntity_;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
public class ServiceSpecification implements Specification<ServiceEntity> {

    private ServiceSearchCriteria criteria;

    @Override
    public Predicate toPredicate(Root<ServiceEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if (criteria != null) {
            if (criteria.getSearch() != null) {
                List<Predicate> searchPredicates = new ArrayList<>();
                Arrays.stream(criteria.getSearch().split(" ")).map(String::toLowerCase).forEach(lowercaseWord ->
                        searchPredicates.add(
                                criteriaBuilder.or(
                                        criteriaBuilder.like(criteriaBuilder.lower(root.get(ServiceEntity_.name)), String.format("%%%s%%", lowercaseWord)),
                                        criteriaBuilder.or(
                                                criteriaBuilder.like(criteriaBuilder.lower(root.get(ServiceEntity_.description)), String.format("%%%s%%", lowercaseWord)),
                                                criteriaBuilder.or(
                                                        criteriaBuilder.like(criteriaBuilder.lower(root.get(ServiceEntity_.url)), String.format("%%%s%%", lowercaseWord)),
                                                        criteriaBuilder.like(criteriaBuilder.lower(root.get(ServiceEntity_.websiteUrl)), String.format("%%%s%%", lowercaseWord))
                                                )
                                        )
                                )
                        )
                );
                predicates.add(criteriaBuilder.or(searchPredicates.toArray(new Predicate[0])));
            }
            if (criteria.getCategories() != null && !criteria.getCategories().isEmpty()) {
                predicates.add(
                        root.join(ServiceEntity_.serviceCategoryLinkEntities).get(ServiceCategoryLinkEntity_.serviceCategoryEntity).get(ServiceCategoryEntity_.name)
                                .in(criteria.getCategories())
                );
            }
            if (criteria.getMaxFlatFee() != null) {
                predicates.add(
                        criteriaBuilder.lessThanOrEqualTo(
                                root.get(ServiceEntity_.flatFee),
                                criteria.getMaxFlatFee()
                        )
                );
            }
            if (criteria.getMaxPercentFee() != null) {
                predicates.add(
                        criteriaBuilder.lessThanOrEqualTo(
                                root.get(ServiceEntity_.percentFee),
                                criteria.getMaxPercentFee()
                        )
                );
            }
            if (criteria.getMinCapacities() != null && !criteria.getMinCapacities().isEmpty()) {
                List<Predicate> minCapacityPredicates = new ArrayList<>();
                criteria.getMinCapacities().forEach(minCapacity -> {
                    String[] splitMinCapacity = minCapacity.split(" ");
                    BigDecimal value = new BigDecimal(splitMinCapacity[0]);
                    String unit = splitMinCapacity[1];
                    minCapacityPredicates.add(
                            criteriaBuilder.and(
                                    criteriaBuilder.greaterThanOrEqualTo(root.join(ServiceEntity_.serviceCapacityEntities).get(ServiceCapacityEntity_.value), value),
                                    criteriaBuilder.equal(criteriaBuilder.lower(root.join(ServiceEntity_.serviceCapacityEntities).get(ServiceCapacityEntity_.unit)), unit.toLowerCase())
                            )
                    );
                });
                predicates.add(criteriaBuilder.or(minCapacityPredicates.toArray(new Predicate[0])));
            }
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
