package com.peachub.app.service;

import com.peachub.app.dto.album.AlbumForm;
import com.peachub.app.entity.Album;
import com.peachub.app.exception.AlbumNotFoundException;
import com.peachub.app.mapper.AlbumMapper;
import com.peachub.app.repository.AlbumRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class AlbumService {
    @Autowired
    private AlbumRepository albumRepository;
    @Autowired
    private AlbumMapper albumMapper;

    public List<Album> getAllAlbums() {
        return albumRepository.findAll();
    }
    @Cacheable(value = "albums", key = "#id")
    public Album getAlbumById(Long id) {
        log.info("Получение альбома с id={}", id);
        log.info("CACHE MISS album={}", id);
        return albumRepository.findById(id).orElseThrow(() -> {
                    log.warn("Альбом с id={} не найден", id);
                    return new AlbumNotFoundException(id);});
    }
    @Cacheable(value = "musicbrainz", key = "#musicBrainzId")
    public Album findByMusicBrainzId(String musicBrainzId) {
        return albumRepository.findByMusicBrainzId(musicBrainzId).orElse(null);
    }
    public void createAlbum(AlbumForm form) {
        log.info("Создание альбома {}", form.getTitle());
        Album album = albumMapper.fromForm(form);
        album = albumRepository.save(album);
        log.info("Альбом создан, id={}", album.getId());
    }
    @CacheEvict(value = "albums", key = "#id")
    public void updateAlbum(Long id, AlbumForm form) {
        Album album = getAlbumById(id);
        album.setTitle(form.getTitle());
        album.setArtist(form.getArtist());
        album.setGenre(form.getGenre());
        album.setReleaseYear(form.getReleaseYear());
        album.setCoverUrl(form.getCoverUrl());
        album.setMusicBrainzId(form.getMusicBrainzId());

        albumRepository.save(album);
    }
    @CacheEvict(value = "albums", key = "#id")
    public void deleteAlbum(Long id) {
        albumRepository.deleteById(id);
    }
    public List<Album> search(String query) {
        return albumRepository.searchAlbums(query);
    }
    public List<Album> searchByTitle(String query) {
        return albumRepository.findByTitleContainingIgnoreCase(query);
    }
    public List<Album> searchByArtist(String artist) {
        return albumRepository.findByArtistContainingIgnoreCase(artist);
    }
    public List<Album> searchByGenre(String genre) {
        return albumRepository.findByGenreContainingIgnoreCase(genre);
    }
    public Album save(Album album) {
        return albumRepository.save(album);
    }
    public List<Album> searchCriteria(String title, String artist, String genre
    ) {
        return albumRepository.searchAlbumsCriteria(title, artist, genre);
    }
    public List<Album> getAlbumsWithRatingAbove(Double rating) {
        return albumRepository.findAlbumsWithRatingAbove(rating);
    }
    @PostConstruct
    public void test() {
        System.out.println("AlbumService class = " + this.getClass());
    }
}