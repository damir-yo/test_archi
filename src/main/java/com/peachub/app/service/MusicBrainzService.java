package com.peachub.app.service;

import com.peachub.app.dto.musicbrainz.MusicBrainzAlbumDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class MusicBrainzService {

    @Autowired
    @Qualifier("musicBrainzRestClient")
    private RestClient restClient;

    public List<MusicBrainzAlbumDto> searchAlbums(String query) {
        System.out.println("USER QUERY = " + query);
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
                (List<Map>) response.get("release-groups");
        System.out.println(
                "MB RESULTS = " +
                        (releaseGroups == null ? 0 : releaseGroups.size())
        );
        if (releaseGroups == null || releaseGroups.isEmpty()) {
            return List.of();
        }

        List<MusicBrainzAlbumDto> albums = new ArrayList<>();

        for (int i = 0; i < Math.min(40, releaseGroups.size()); i++) {

            Map result = releaseGroups.get(i);

            String title =
                    (String) result.get("title");

            String musicBrainzId =
                    (String) result.get("id");

            String artist = "";

            var artistCredits =
                    (List<Map>) result.get("artist-credit");

            if (artistCredits != null &&
                    !artistCredits.isEmpty()) {

                Map firstArtistCredit =
                        artistCredits.get(0);

                Map artistMap =
                        (Map) firstArtistCredit.get("artist");

                artist =
                        (String) artistMap.get("name");
            }
            System.out.println(
                    "SCORE -> "
                            + title
                            + " | "
                            + artist
            );
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
                    year = Integer.parseInt(
                            firstReleaseDate.substring(0, 4)
                    );
                } catch (NumberFormatException ignored) {
                }
            }
            System.out.println(
                    "FOUND MB ALBUM -> " +
                            title +
                            " | " +
                            artist +
                            " | " +
                            musicBrainzId
            );
            System.out.println("RAW RESULT = " + result);
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
        System.out.println("FINAL ALBUMS = " + albums.size());
        return albums;
    }
}