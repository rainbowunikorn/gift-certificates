package com.epam.esm.repository;

import com.epam.esm.entity.Tag;
import com.epam.esm.mapper.TagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class TagRepository extends AbstractRepository<Tag> {
    private static final String INSERT_TAG_QUERY = "INSERT INTO tags(name) VALUES (?);";

    @Autowired
    protected TagRepository(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    protected String getTableName() {
        return Tag.TAGS_TABLE_NAME;
    }

    @Override
    protected RowMapper<Tag> getRowMapper() {
        return new TagMapper();
    }

    @Override
    public Long save(Tag tag) {
        String name = tag.getName();
        return insertData(INSERT_TAG_QUERY, name);
    }
}
