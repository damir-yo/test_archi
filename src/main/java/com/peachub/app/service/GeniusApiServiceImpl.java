package com.peachub.app.service;

import com.peachub.app.config.GeniusConfig;
import com.peachub.app.dto.genius.GeniusAlbumDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Service
public class GeniusApiServiceImpl implements GeniusApiService {

    private final RestClient restClient;
    private final GeniusConfig geniusConfig;

    public GeniusApiServiceImpl(GeniusConfig geniusConfig) {

        this.geniusConfig = geniusConfig;

        this.restClient = RestClient.builder()
                .baseUrl("https://api.genius.com")
                .build();
    }

    @Override
    public GeniusAlbumDto searchAlbum(String query) {

        Map<String, Object> response =
                restClient.get()
                        .uri(uriBuilder ->
                                uriBuilder
                                        .path("/search")
                                        .queryParam("q", query)
                                        .build()
                        )
                        .header(
                                HttpHeaders.AUTHORIZATION,
                                "Bearer " + geniusConfig.getToken()
                        )
                        .accept(MediaType.APPLICATION_JSON)
                        .retrieve()
                        .body(Map.class);

        Map responseMap = (Map) response.get("response");

        var hits = (java.util.List<Map>) responseMap.get("hits");

        if (hits.isEmpty()) {
            return null;
        }

        Map firstHit = hits.get(0);

        Map result = (Map) firstHit.get("result");

        String title = (String) result.get("title");

        String imageUrl = (String) result.get("song_art_image_url");

        Map artistMap = (Map) result.get("primary_artist");

        String artist = (String) artistMap.get("name");

        return new GeniusAlbumDto(
                title,
                artist,
                imageUrl
        );
    }
}