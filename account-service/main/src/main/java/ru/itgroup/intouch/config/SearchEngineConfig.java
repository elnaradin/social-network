package ru.itgroup.intouch.config;

import Filters.AccountFilterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import searchUtils.SpecificationBuilder;

@Configuration
public class SearchEngineConfig {

    @Bean
    public AccountFilterBuilder accountFilterBuilder() {
        return new AccountFilterBuilder();
    }

    @Bean
    public SpecificationBuilder accountSpecificationBuilder() {
        return new SpecificationBuilder();
    }
}
