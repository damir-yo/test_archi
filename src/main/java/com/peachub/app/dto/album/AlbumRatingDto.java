package com.peachub.app.dto.album;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AlbumRatingDto {
    private Long albumId;
    private String title;
    private String coverUrl;
    private Double averageRating;
    private Long reviewsCount;
}