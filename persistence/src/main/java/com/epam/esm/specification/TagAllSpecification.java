package com.epam.esm.specification;

import com.epam.esm.entity.Tag;

public class TagAllSpecification implements SqlSpecification {
    @Override
    public String getSqlQuery() {
        return "SELECT * FROM tags;";
    }
}
