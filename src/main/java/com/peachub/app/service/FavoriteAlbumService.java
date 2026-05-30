package com.peachub.app.service;

import com.peachub.app.entity.Album;
import com.peachub.app.entity.FavoriteAlbum;
import com.peachub.app.entity.User;
import com.peachub.app.repository.FavoriteAlbumRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteAlbumService {

    private final FavoriteAlbumRepository favoriteAlbumRepository;
    private final UserService userService;
    private final AlbumService albumService;

    public FavoriteAlbumService(
            FavoriteAlbumRepository favoriteAlbumRepository,
            UserService userService,
            AlbumService albumService
    ) {
        this.favoriteAlbumRepository = favoriteAlbumRepository;
        this.userService = userService;
        this.albumService = albumService;
    }

    public void addToFavorites(
            Long albumId,
            String email
    ) {

        User user = userService.findByEmail(email);

        Album album = albumService.getAlbumById(albumId);

        if (favoriteAlbumRepository.existsByUserIdAndAlbumId(
                user.getId(),
                albumId
        )) {
            return;
        }

        FavoriteAlbum favoriteAlbum = new FavoriteAlbum();

        favoriteAlbum.setUser(user);
        favoriteAlbum.setAlbum(album);

        favoriteAlbumRepository.save(favoriteAlbum);
    }

    public void removeFromFavorites(
            Long albumId,
            String email
    ) {

        User user = userService.findByEmail(email);

        FavoriteAlbum favoriteAlbum =
                favoriteAlbumRepository
                        .findByUserIdAndAlbumId(
                                user.getId(),
                                albumId
                        )
                        .orElseThrow();

        favoriteAlbumRepository.delete(favoriteAlbum);
    }

    public boolean isFavorite(
            String email,
            Long albumId
    ) {

        User user = userService.findByEmail(email);

        return favoriteAlbumRepository
                .existsByUserIdAndAlbumId(
                        user.getId(),
                        albumId
                );
    }

    public List<FavoriteAlbum> getUserFavorites(Long userId) {

        return favoriteAlbumRepository.findByUserId(userId);
    }
}