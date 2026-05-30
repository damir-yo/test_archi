package com.peachub.app.repository;

import com.peachub.app.entity.FavoriteAlbum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoriteAlbumRepository
        extends JpaRepository<FavoriteAlbum, Long> {

    Optional<FavoriteAlbum> findByUserIdAndAlbumId(
            Long userId,
            Long albumId
    );

    List<FavoriteAlbum> findByUserId(Long userId);

    boolean existsByUserIdAndAlbumId(
            Long userId,
            Long albumId
    );
}