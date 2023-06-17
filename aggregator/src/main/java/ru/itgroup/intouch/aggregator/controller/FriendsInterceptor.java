package ru.itgroup.intouch.aggregator.controller;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class FriendsInterceptor {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            if (requestTemplate.path().contains("friends")) {
                requestTemplate.header("currentEmail",
                        SecurityContextHolder.getContext().getAuthentication().getName());
            }
        };
    }
}
