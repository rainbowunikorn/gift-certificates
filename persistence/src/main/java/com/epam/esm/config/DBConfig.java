package com.epam.esm.config;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import javax.sql.DataSource;

@EnableWebMvc
@ComponentScan(basePackages = "com.epam.esm")
@Configuration
@PropertySource("classpath:db/jdbc.properties")
public class DBConfig implements WebMvcConfigurer {

    private static Logger logger = LoggerFactory.getLogger(DBConfig.class);

    @Value("${driverClassName}")
    private String driverClassName;

    @Value("${url}")
    private String url;

    @Value("${username}")
    private String username;

    @Value("${password}")
    private String password;

    @Bean
    @Profile("dev")
    public DataSource H2DataSource() {
        try {
            EmbeddedDatabaseBuilder databaseBuilder = new EmbeddedDatabaseBuilder();
            return databaseBuilder.setType(EmbeddedDatabaseType.H2)
                    .addScripts("classpath:sql/ddl.sql", "classpath:sql/script.sql", "classpath:sql/test-data.sql")
                    .build();
        } catch (Exception e) {
            logger.error("Embedded DataSource bean cannot be created!", e);
            return null;
        }
    }

    @Bean
    @Profile("prod")
    public DataSource PostgresDataSource() {
        HikariConfig config = new HikariConfig();
        config.setDataSourceClassName(driverClassName);
        config.setUsername(username);
        config.setPassword(password);
        //config.setMaximumPoolSize();

        return new HikariDataSource(config);
    }

    @Bean
    public JdbcTemplate getJdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

}
