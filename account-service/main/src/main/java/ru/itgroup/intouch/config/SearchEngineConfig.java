package ru.itgroup.intouch.config;

import filters.AccountFilterBuilder;
import mappers.AccountDtoPageableMapper;
import org.modelmapper.ModelMapper;
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

    @Bean
    public AccountDtoPageableMapper dtoPageableMapper() {
        return new AccountDtoPageableMapper();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
