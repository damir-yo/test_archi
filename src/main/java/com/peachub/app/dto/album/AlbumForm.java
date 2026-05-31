package com.peachub.app.dto.album;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlbumForm {

    @NotBlank(message = "Title cannot be empty")
    private String title;

    @NotBlank(message = "Artist cannot be empty")
    private String artist;

    private String coverUrl;

    @Min(value = 1900, message = "Release year is invalid")
    private int releaseYear;

    @NotBlank(message = "Genre cannot be empty")
    private String genre;

    private String musicBrainzId;
}