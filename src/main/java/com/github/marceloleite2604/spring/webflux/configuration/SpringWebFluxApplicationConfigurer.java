package com.github.marceloleite2604.spring.webflux.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringWebFluxApplicationConfigurer {

    @Bean
    public ObjectMapper createObjectMapper() {
        return new ObjectMapper();
    }
}
