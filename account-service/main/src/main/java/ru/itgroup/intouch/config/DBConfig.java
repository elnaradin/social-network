package ru.itgroup.intouch.config;

import config.SpringConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DBConfig {
    @Bean
    public SpringConfig springConfig() {
        return new SpringConfig();
    }

    @Bean
    public DataSource dataSource() {
        return springConfig().dataSource();
    }
}
