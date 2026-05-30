package com.peachub.app.dto.externalAlbum;

public record ExternalAlbumDto(
        String title,
        String artist,
        Integer releaseYear,
        String genre,
        String coverUrl
) {}
