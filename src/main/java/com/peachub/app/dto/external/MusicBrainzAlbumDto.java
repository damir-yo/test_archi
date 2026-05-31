package com.peachub.app.dto.external;

public record MusicBrainzAlbumDto(
        String musicBrainzId,
        String title,
        String artist,
        Integer releaseYear,
        String genre
) {}