package com.peachub.app.service;

import com.peachub.app.dto.external.MusicBrainzAlbumDto;
import com.peachub.app.exception.ExternalApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class MusicBrainzService {
    @Autowired
    @Qualifier("musicBrainzRestClient")
    private RestClient restClient;

    @Cacheable(value = "musicbrainz", key = "#query")
    public List<MusicBrainzAlbumDto> searchAlbums(String query) {
        log.info("Поиск альбомов в MusicBrainz, query={}", query);
        Map<String, Object> response;
        try {
            response =
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

        } catch (Exception e) {

        log.error("Ошибка обращения к MusicBrainz API", e);

        throw new ExternalApiException("MusicBrainz API is unavailable");
    }
        var releaseGroups = (List<Map>) response.get("release-groups");
        log.info("Найдено результатов в MusicBrainz: {}", releaseGroups == null ? 0 : releaseGroups.size());
        if (releaseGroups == null || releaseGroups.isEmpty()) {
            return List.of();
        }

        List<MusicBrainzAlbumDto> albums = new ArrayList<>();

        for (int i = 0; i < Math.min(40, releaseGroups.size()); i++) {
            Map result = releaseGroups.get(i);
            String title = (String) result.get("title");

            String musicBrainzId = (String) result.get("id");

            String artist = "";

            var artistCredits = (List<Map>) result.get("artist-credit");

            if (artistCredits != null && !artistCredits.isEmpty()) {
                Map firstArtistCredit = artistCredits.get(0);
                Map artistMap = (Map) firstArtistCredit.get("artist");
                artist = (String) artistMap.get("name");
            }
            Integer year = null;
            String genre = null;

            var tags = (List<Map>) result.get("tags");

            if (tags != null && !tags.isEmpty()) {
                Map firstTag = tags.get(0);
                genre = (String) firstTag.get("name");
            }
            String firstReleaseDate =
                    (String) result.get("first-release-date");

            if (firstReleaseDate != null &&
                    firstReleaseDate.length() >= 4) {
                try {
                    year = Integer.parseInt(firstReleaseDate.substring(0, 4));
                } catch (NumberFormatException ignored) {
                }
            }
            albums.add(
                    new MusicBrainzAlbumDto(
                            musicBrainzId,
                            title,
                            artist,
                            year,
                            genre
                    )
            );
        }
        log.info("Возвращено альбомов: {}", albums.size());
        return albums;
    }
}