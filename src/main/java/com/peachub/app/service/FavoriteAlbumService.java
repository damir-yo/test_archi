package com.peachub.app.service;

import com.peachub.app.entity.Album;
import com.peachub.app.entity.FavoriteAlbum;
import com.peachub.app.entity.User;
import com.peachub.app.exception.FavoriteAlbumNotFoundException;
import com.peachub.app.repository.FavoriteAlbumRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class FavoriteAlbumService {
    @Autowired
    private FavoriteAlbumRepository favoriteAlbumRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private AlbumService albumService;

    public void addToFavorites(Long albumId, String email) {
        log.info("Пользователь {} добавляет альбом {} в избранное", email, albumId);
        User user = userService.findByEmail(email);
        Album album = albumService.getAlbumById(albumId);

        if (favoriteAlbumRepository.existsByUserIdAndAlbumId(user.getId(), albumId)) {
            log.warn("Альбом {} уже находится в избранном пользователя {}", albumId, email);

            return;
        }

        FavoriteAlbum favoriteAlbum = new FavoriteAlbum();

        favoriteAlbum.setUser(user);
        favoriteAlbum.setAlbum(album);

        favoriteAlbumRepository.save(favoriteAlbum);
        log.info("Альбом {} успешно добавлен в избранное пользователя {}", albumId, email);
    }

    public void removeFromFavorites(Long albumId, String email) {
        log.info("Пользователь {} удаляет альбом {} из избранного", email, albumId);
        User user = userService.findByEmail(email);

        FavoriteAlbum favoriteAlbum = favoriteAlbumRepository.findByUserIdAndAlbumId(user.getId(), albumId)
                        .orElseThrow(() -> {
                            log.warn("Избранный альбом {} не найден у пользователя {}", albumId, email);

                            return new FavoriteAlbumNotFoundException(albumId);
                        });

        favoriteAlbumRepository.delete(favoriteAlbum);
        log.info("Альбом {} удалён из избранного пользователя {}", albumId, email);
    }

    public boolean isFavorite(String email, Long albumId) {
        User user = userService.findByEmail(email);
        return favoriteAlbumRepository.existsByUserIdAndAlbumId(user.getId(), albumId);
    }

    public List<FavoriteAlbum> getUserFavorites(Long userId) {
        return favoriteAlbumRepository.findByUserId(userId);
    }
}