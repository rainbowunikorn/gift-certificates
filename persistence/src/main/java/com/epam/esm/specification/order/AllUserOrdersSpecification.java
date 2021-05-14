package com.epam.esm.specification.order;

import com.epam.esm.entity.Order;
import com.epam.esm.specification.CriteriaSpecification;
import lombok.RequiredArgsConstructor;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@RequiredArgsConstructor
public class AllUserOrdersSpecification implements CriteriaSpecification<Order> {
    private final Long userId;

    @Override
    public CriteriaQuery<Order> getCriteriaQuery(CriteriaBuilder builder) {
        CriteriaQuery<Order> criteria = builder.createQuery(Order.class);
        Root<Order> orderRoot = criteria.from(Order.class);
        Predicate ordersByUserIdPredicate = builder.equal(orderRoot.get("user_id"), userId);
        Predicate isNotDeletedPredicate = builder.isFalse(orderRoot.get("isDeleted"));
        criteria.select(orderRoot).distinct(true).where(ordersByUserIdPredicate,
                isNotDeletedPredicate);
        return criteria;
    }
}