package com.peachub.app.controller;

import com.peachub.app.dto.AlbumForm;
import com.peachub.app.entity.Album;
import com.peachub.app.service.AlbumService;
import com.peachub.app.service.FavoriteAlbumService;
import com.peachub.app.service.ReviewService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/albums")
@AllArgsConstructor
public class AlbumController {
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private AlbumService albumService;
    @Autowired
    private FavoriteAlbumService favoriteAlbumService;

    @GetMapping("/{id}")
    public String albumPage(
            @PathVariable Long id,
            Model model,
            Authentication authentication
    ) {

        Album album = albumService.getAlbumById(id);

        model.addAttribute("album", album);

        model.addAttribute(
                "reviews",
                reviewService.findByAlbumId(id)
        );

        boolean isFavorite = false;

        if (authentication != null) {

            isFavorite = favoriteAlbumService.isFavorite(
                    authentication.getName(),
                    album.getId()
            );
        }

        model.addAttribute("isFavorite", isFavorite);

        return "album";
    }

    @GetMapping
    public String albumsPage(
            @RequestParam(required = false)
            String title,

            @RequestParam(required = false)
            String artist,

            @RequestParam(required = false)
            String genre,

            Model model
    ) {

        if (title != null && !title.isBlank()) {

            model.addAttribute(
                    "albums",
                    albumService.searchByTitle(title)
            );

        } else if (artist != null && !artist.isBlank()) {

            model.addAttribute(
                    "albums",
                    albumService.searchByArtist(artist)
            );

        } else if (genre != null && !genre.isBlank()) {

            model.addAttribute(
                    "albums",
                    albumService.searchByGenre(genre)
            );

        } else {

            model.addAttribute(
                    "albums",
                    albumService.getAllAlbums()
            );
        }

        return "albums";
    }

    @GetMapping("/search")
    public String searchAlbums(
            @RequestParam String query,
            Model model
    ) {

        model.addAttribute(
                "albums",
                albumService.search(query)
        );

        return "albums";
    }

    @GetMapping("/new")
    public String createAlbumPage(Model model) {

        model.addAttribute(
                "albumForm",
                new AlbumForm()
        );

        return "create-album";
    }

    @PostMapping
    public String createAlbum(
            @Valid AlbumForm albumForm,
            BindingResult bindingResult
    ) {

        if (bindingResult.hasErrors()) {
            return "create-album";
        }

        albumService.createAlbum(albumForm);

        return "redirect:/albums";
    }

    @GetMapping("/{id}/edit")
    public String editAlbumPage(
            @PathVariable Long id,
            Model model
    ) {

        Album album = albumService.getAlbumById(id);

        AlbumForm form = new AlbumForm();

        form.setTitle(album.getTitle());
        form.setArtist(album.getArtist());
        form.setGenre(album.getGenre());
        form.setReleaseYear(album.getReleaseYear());
        form.setCoverUrl(album.getCoverUrl());
        form.setExternalId(album.getExternalId());

        model.addAttribute("albumForm", form);

        model.addAttribute("albumId", id);

        return "edit-album";
    }

    @PostMapping("/{id}/edit")
    public String updateAlbum(
            @PathVariable Long id,
            @Valid AlbumForm albumForm,
            BindingResult bindingResult
    ) {

        if (bindingResult.hasErrors()) {
            return "edit-album";
        }

        albumService.updateAlbum(id, albumForm);

        return "redirect:/albums";
    }

    @PostMapping("/{id}/delete")
    public String deleteAlbum(
            @PathVariable Long id
    ) {

        albumService.deleteAlbum(id);

        return "redirect:/albums";
    }

    @GetMapping("/top")
    public String topAlbumsPage(Model model) {

        model.addAttribute(
                "topAlbums",
                reviewService.getTopAlbums()
        );

        return "top-albums";
    }
}