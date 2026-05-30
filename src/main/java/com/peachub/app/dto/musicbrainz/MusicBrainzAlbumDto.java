package com.peachub.app.dto.musicbrainz;

public record MusicBrainzAlbumDto(
        String musicBrainzId,
        String title,
        String artist,
        Integer releaseYear,
        String genre
) {}