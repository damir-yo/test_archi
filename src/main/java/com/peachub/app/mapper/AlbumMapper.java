package com.peachub.app.mapper;

import com.peachub.app.dto.album.AlbumForm;
import com.peachub.app.dto.album.AlbumResponseDto;
import com.peachub.app.entity.Album;
import org.springframework.stereotype.Component;

@Component
public class AlbumMapper {

    public Album fromForm(AlbumForm form) {

        Album album = new Album();

        album.setTitle(form.getTitle());
        album.setArtist(form.getArtist());
        album.setGenre(form.getGenre());
        album.setReleaseYear(form.getReleaseYear());
        album.setCoverUrl(form.getCoverUrl());
        album.setMusicBrainzId(form.getMusicBrainzId());

        return album;
    }

    public AlbumResponseDto toDto(Album album) {
        return new AlbumResponseDto(
                album.getId(),
                album.getTitle(),
                album.getArtist(),
                album.getGenre(),
                album.getReleaseYear(),
                album.getCoverUrl()
        );
    }
    public AlbumForm toForm(Album album) {

        AlbumForm form = new AlbumForm();

        form.setTitle(album.getTitle());
        form.setArtist(album.getArtist());
        form.setGenre(album.getGenre());
        form.setReleaseYear(album.getReleaseYear());
        form.setCoverUrl(album.getCoverUrl());
        form.setMusicBrainzId(album.getMusicBrainzId());

        return form;
    }
}
