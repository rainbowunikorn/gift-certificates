package com.epam.esm.repository;

import com.epam.esm.entity.BaseEntity;
import com.epam.esm.specification.CriteriaSpecification;

import java.util.List;
import java.util.Optional;

public interface Repository<T extends BaseEntity> {
    T save(T entity);

    T update(T entity);

    void delete(T entity);

    Optional<T> getById(Long id);

    Optional<T> getByName(String name);

    Integer countEntities(CriteriaSpecification<T> specification);

    List<T> getEntityListBySpecification(CriteriaSpecification<T> specification, Integer pageNumber, Integer pageSize);

    Optional<T> getEntityBySpecification(CriteriaSpecification<T> specification);
}
