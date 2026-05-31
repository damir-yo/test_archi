package com.peachub.app.config;

import lombok.Getter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
@Getter
public class ExternalApiConfig {

    @Bean
    public RestClient musicBrainzRestClient() {
        return RestClient.builder().baseUrl("https://musicbrainz.org/ws/2").build();
    }

    @Bean
    public RestClient coverArtRestClient() {
        return RestClient.builder().baseUrl("https://coverartarchive.org").build();
    }
}