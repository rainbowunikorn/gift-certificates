package com.epam.esm.specification;

import com.epam.esm.entity.Certificate;

public class CertificateAllSpecification implements SqlSpecification<Certificate> {

    @Override
    public String getSqlQuery() {
        return "SELECT * FROM gift_certificates;";
    }
}