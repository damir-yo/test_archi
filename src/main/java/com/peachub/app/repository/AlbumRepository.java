package com.peachub.app.repository;

import com.peachub.app.entity.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AlbumRepository extends JpaRepository<Album, Long> {
    Optional<Album> findByMusicBrainzId(String musicBrainzId);
    List<Album> findByTitleContainingIgnoreCase(String title);
    List<Album> findByArtistContainingIgnoreCase(String artist);

    List<Album> findByGenreContainingIgnoreCase(String genre);

    @Query("""
    SELECT a FROM Album a
    WHERE LOWER(a.title) LIKE LOWER(CONCAT('%', :query, '%'))
    OR LOWER(a.artist) LIKE LOWER(CONCAT('%', :query, '%'))
    OR LOWER(a.genre) LIKE LOWER(CONCAT('%', :query, '%'))
    """)
    List<Album> searchAlbums(String query);
}