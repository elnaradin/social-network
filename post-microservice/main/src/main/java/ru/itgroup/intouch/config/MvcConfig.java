package ru.itgroup.intouch.config;


import filters.PostFilterBuilder;
import mappers.PostDtoPageableMapper;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import searchUtils.SpecificationBuilder;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public PostFilterBuilder filterBuilder() {
        return new PostFilterBuilder();
    }

    @Bean
    public SpecificationBuilder specificationBuilder() {
        return new SpecificationBuilder();
    }

    @Bean
    public PostDtoPageableMapper postDtoPageableMapper() {
        return new PostDtoPageableMapper();
    }

    @Bean
    public JWTUtil jwtUtil() {
        return new JWTUtil();
    }


}
