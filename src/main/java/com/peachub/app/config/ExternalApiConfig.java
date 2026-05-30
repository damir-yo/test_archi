package com.peachub.app.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
@Getter
public class ExternalApiConfig {

    @Value("${genius.token}")
    private String token;

    @Bean
    public RestClient geniusRestClient() {
        return RestClient.builder()
                .baseUrl("https://api.genius.com")
                .defaultHeader(
                        "Authorization",
                        "Bearer " + token
                )
                .build();
    }

    @Bean
    public RestClient musicBrainzRestClient() {
        return RestClient.builder()
                .baseUrl("https://musicbrainz.org/ws/2")
                .build();
    }

    @Bean
    public RestClient coverArtRestClient() {
        return RestClient.builder()
                .baseUrl("https://coverartarchive.org")
                .build();
    }
}