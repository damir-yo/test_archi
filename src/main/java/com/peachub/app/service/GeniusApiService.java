package com.peachub.app.service;

import com.peachub.app.config.ExternalApiConfig;
import com.peachub.app.dto.genius.GeniusAlbumDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Service
public class GeniusApiService {
    @Autowired
    @Qualifier("geniusRestClient")
    private RestClient restClient;

    @Autowired
    private ExternalApiConfig externalApiConfig;

    public GeniusAlbumDto searchAlbum(String query) {

        try {

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
                                    "Bearer " + externalApiConfig.getToken()
                            )
                            .accept(MediaType.APPLICATION_JSON)
                            .retrieve()
                            .body(Map.class);

            if (response == null) {
                return null;
            }

            Map responseMap = (Map) response.get("response");

            if (responseMap == null) {
                return null;
            }

            var hits = (java.util.List<Map>) responseMap.get("hits");
            System.out.println("HITS COUNT = " + hits.size());
            if (hits == null || hits.isEmpty()) {
                return null;
            }

            for (int i = 0; i < Math.min(5, hits.size()); i++) {

                Map hit = hits.get(i);

                Map result = (Map) hit.get("result");

                String title =
                        (String) result.get("title");

                Map artistMap =
                        (Map) result.get("primary_artist");

                String artist =
                        (String) artistMap.get("name");

                System.out.println(
                        "GENIUS HIT " + i +
                                " -> " +
                                title +
                                " | " +
                                artist
                );
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

        } catch (Exception e) {

            System.out.println("Genius API error: " + e.getMessage());

            return null;
        }
    }
}