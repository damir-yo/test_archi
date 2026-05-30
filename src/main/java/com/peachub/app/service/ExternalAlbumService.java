package com.peachub.app.service;

import com.peachub.app.dto.externalAlbum.ExternalAlbumDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExternalAlbumService {

    @Autowired
    private MusicBrainzService musicBrainzService;

    @Autowired
    private CoverArtArchiveService coverArtArchiveService;

    public List<ExternalAlbumDto> searchAlbums(String query) {

        var musicBrainzAlbums =
                musicBrainzService.searchAlbums(query);

        return musicBrainzAlbums.stream()
                .map(album -> {

                    String coverUrl =
                            coverArtArchiveService.getCoverUrl(
                                    album.musicBrainzId()
                            );

                    if (coverUrl == null) {
                        return null;
                    }
                    System.out.println(
                            "FINAL COVER = " + coverUrl
                    );
                    System.out.println(
                            "GENRE = " + album.genre()
                    );
                    System.out.println(
                            "EXTERNAL DTO GENRE = " + album.genre()
                    );
                    return new ExternalAlbumDto(
                            album.musicBrainzId(),
                            album.title(),
                            album.artist(),
                            album.releaseYear(),
                            album.genre(),
                            coverUrl
                    );
                })
                .filter(java.util.Objects::nonNull)
                .toList();

    }

}