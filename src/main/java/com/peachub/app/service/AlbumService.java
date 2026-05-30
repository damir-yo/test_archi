package com.peachub.app.service;

import com.peachub.app.dto.AlbumForm;
import com.peachub.app.entity.Album;
import com.peachub.app.repository.AlbumRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlbumService {

    private final AlbumRepository albumRepository;

    public AlbumService(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    public List<Album> getAllAlbums() {
        return albumRepository.findAll();
    }

    public Album getAlbumById(Long id) {
        return albumRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Album not found"));
    }

    public void createAlbum(AlbumForm form) {

        Album album = new Album();

        album.setTitle(form.getTitle());
        album.setArtist(form.getArtist());
        album.setGenre(form.getGenre());
        album.setReleaseYear(form.getReleaseYear());
        album.setCoverUrl(form.getCoverUrl());
        album.setExternalId(form.getExternalId());

        albumRepository.save(album);
    }

    public void updateAlbum(Long id, AlbumForm form) {

        Album album = getAlbumById(id);

        album.setTitle(form.getTitle());
        album.setArtist(form.getArtist());
        album.setGenre(form.getGenre());
        album.setReleaseYear(form.getReleaseYear());
        album.setCoverUrl(form.getCoverUrl());
        album.setExternalId(form.getExternalId());

        albumRepository.save(album);
    }

    public void deleteAlbum(Long id) {
        albumRepository.deleteById(id);
    }
    public List<Album> search(String query) {

        return albumRepository.searchAlbums(query);
    }
    public List<Album> searchByTitle(String query) {

        return albumRepository
                .findByTitleContainingIgnoreCase(query);
    }

    public List<Album> searchByArtist(String artist) {

        return albumRepository
                .findByArtistContainingIgnoreCase(artist);
    }

    public List<Album> searchByGenre(String genre) {

        return albumRepository
                .findByGenreContainingIgnoreCase(genre);
    }
}