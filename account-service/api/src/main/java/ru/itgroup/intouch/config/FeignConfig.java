package ru.itgroup.intouch.config;

import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class FeignConfig {

    @Bean
    public ErrorDecoder decoder() {

        return new FeignErrorDecoder();
    }
}
