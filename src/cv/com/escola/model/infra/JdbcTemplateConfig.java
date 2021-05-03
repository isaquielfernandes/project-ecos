package cv.com.escola.model.infra;

import cv.com.escola.model.dao.db.HikariCPDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class JdbcTemplateConfig {
    
    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(HikariCPDataSource.dataSource());
    }
}
