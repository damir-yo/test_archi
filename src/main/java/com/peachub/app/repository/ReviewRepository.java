package com.peachub.app.repository;

import com.peachub.app.dto.AlbumRatingDto;
import com.peachub.app.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository
        extends JpaRepository<Review, Long> {

    List<Review> findByAlbumId(Long albumId);

    List<Review> findByUserId(Long userId);

    @Query("""
    SELECT new com.peachub.app.dto.AlbumRatingDto(
        r.album.id,
        r.album.title,
        r.album.coverUrl,
        AVG(r.rating),
        COUNT(r)
    )
    FROM Review r
    GROUP BY r.album.id, r.album.title, r.album.coverUrl
    ORDER BY AVG(r.rating) DESC
""")
    List<AlbumRatingDto> getTopAlbums();

}