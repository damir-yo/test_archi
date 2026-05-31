package com.peachub.app.repository;

import com.peachub.app.entity.Album;

import java.util.List;

public interface AlbumCriteriaRepository {
    List<Album> searchAlbumsCriteria(
            String title,
            String artist,
            String genre
    );
}