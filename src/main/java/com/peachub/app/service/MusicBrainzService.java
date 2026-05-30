package com.peachub.app.service;

import com.peachub.app.dto.musicbrainz.MusicBrainzAlbumDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Map;

@Service
public class MusicBrainzService {

    @Autowired
    @Qualifier("musicBrainzRestClient")
    private RestClient restClient;

    public MusicBrainzAlbumDto searchAlbum(String query) {

        Map<String, Object> response =
                restClient.get()
                        .uri(uriBuilder ->
                                uriBuilder
                                        .path("/release-group")
                                        .queryParam("query", query)
                                        .queryParam("fmt", "json")
                                        .build()
                        )
                        .header(
                                HttpHeaders.USER_AGENT,
                                "PeachHub/1.0 (student project)"
                        )
                        .retrieve()
                        .body(Map.class);

        var releaseGroups =
                (java.util.List<Map>)
                        response.get("release-groups");

        if (releaseGroups.isEmpty()) {
            return null;
        }

        Map firstResult = releaseGroups.get(0);

        String title =
                (String) firstResult.get("title");

        String artist = "";

        var artistCredits =
                (java.util.List<Map>)
                        firstResult.get("artist-credit");

        if (!artistCredits.isEmpty()) {

            Map firstArtistCredit =
                    artistCredits.get(0);

            Map artistMap =
                    (Map) firstArtistCredit.get("artist");

            artist =
                    (String) artistMap.get("name");
        }

        Integer year = null;

        String firstReleaseDate =
                (String) firstResult.get("first-release-date");

        if (firstReleaseDate != null
                && firstReleaseDate.length() >= 4) {

            year =
                    Integer.parseInt(
                            firstReleaseDate.substring(0, 4)
                    );
        }

        return new MusicBrainzAlbumDto(
                title,
                artist,
                year,
                null
        );
    }
}