package com.epam.esm.config;

import com.epam.esm.repository.CertificateRepositoryImpl;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.repository.TagRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@Profile("dev")
public class TestConfig {

    @Bean
    public DataSource h2DataSource() {
        EmbeddedDatabaseBuilder databaseBuilder = new EmbeddedDatabaseBuilder();
        return databaseBuilder.setType(EmbeddedDatabaseType.H2)
                .setName("test")
                .addScript("classpath:sql/script.sql")
                .addScript("classpath:sql/test-data.sql")
                .build();
    }


    @Bean
    public TagRepository tagRepository() {
        return new TagRepositoryImpl(h2DataSource());
    }

    @Bean
    public CertificateRepositoryImpl certificateRepositoryImpl() {
        return new CertificateRepositoryImpl(h2DataSource());
    }

    @Bean
    public PlatformTransactionManager txManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
