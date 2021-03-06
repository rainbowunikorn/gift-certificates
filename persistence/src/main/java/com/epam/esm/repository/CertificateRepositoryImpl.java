package com.epam.esm.repository;

import com.epam.esm.entity.Certificate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class CertificateRepositoryImpl extends AbstractRepository<Certificate> {

    public CertificateRepositoryImpl(EntityManager entityManager) {
        super(entityManager, Certificate.class);
    }
}
