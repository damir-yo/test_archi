package com.peachub.app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AlbumRatingDto {
    private Long albumId;
    private String title;
    private String coverUrl;
    private Double averageRating;
    private Long reviewsCount;
}