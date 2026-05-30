package com.peachub.app.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Getter
@Configuration
public class GeniusConfig {

    @Value("${genius.token}")
    private String token;

    @Bean
    public RestClient restClient() {
        return RestClient.builder()
                .defaultHeader(
                        "Authorization",
                        "Bearer " + token
                )
                .build();
    }
}