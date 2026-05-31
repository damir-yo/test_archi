package com.peachub.app.dto.external;

public record ExternalAlbumDto(
        String musicBrainzId,
        String title,
        String artist,
        Integer releaseYear,
        String genre,
        String coverUrl
) {}
