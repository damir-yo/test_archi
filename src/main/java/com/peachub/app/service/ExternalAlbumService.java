package com.peachub.app.service;

import com.peachub.app.dto.externalAlbum.ExternalAlbumDto;
import com.peachub.app.dto.genius.GeniusAlbumDto;
import com.peachub.app.dto.musicbrainz.MusicBrainzAlbumDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExternalAlbumService {

    @Autowired
    private GeniusApiService geniusApiService;

    @Autowired
    private MusicBrainzService musicBrainzService;

    public ExternalAlbumDto searchAlbum(String query) {

        GeniusAlbumDto geniusAlbum =
                geniusApiService.searchAlbum(query);

        MusicBrainzAlbumDto musicBrainzAlbum =
                musicBrainzService.searchAlbum(query);

        return new ExternalAlbumDto(
                geniusAlbum != null
                        ? geniusAlbum.title()
                        : null,

                geniusAlbum != null
                        ? geniusAlbum.artist()
                        : null,

                musicBrainzAlbum != null
                        ? musicBrainzAlbum.releaseYear()
                        : null,

                musicBrainzAlbum != null
                        ? musicBrainzAlbum.genre()
                        : null,

                geniusAlbum != null
                        ? geniusAlbum.coverUrl()
                        : null
        );
    }
}