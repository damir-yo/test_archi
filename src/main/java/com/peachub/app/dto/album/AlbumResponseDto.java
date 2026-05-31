package com.peachub.app.dto.album;

public record AlbumResponseDto(
        Long id,
        String title,
        String artist,
        String genre,
        Integer releaseYear,
        String coverUrl
) {}