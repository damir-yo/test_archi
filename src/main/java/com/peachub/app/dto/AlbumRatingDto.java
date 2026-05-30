package com.peachub.app.dto;

public class AlbumRatingDto {

    private Long albumId;
    private String title;
    private String coverUrl;
    private Double averageRating;
    private Long reviewsCount;

    public AlbumRatingDto(
            Long albumId,
            String title,
            String coverUrl,
            Double averageRating,
            Long reviewsCount
    ) {
        this.albumId = albumId;
        this.title = title;
        this.coverUrl = coverUrl;
        this.averageRating = averageRating;
        this.reviewsCount = reviewsCount;
    }

    public Long getAlbumId() {
        return albumId;
    }

    public String getTitle() {
        return title;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public Long getReviewsCount() {
        return reviewsCount;
    }
}