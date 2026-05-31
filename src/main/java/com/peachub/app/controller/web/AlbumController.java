package com.peachub.app.controller.web;

import com.peachub.app.dto.album.AlbumForm;
import com.peachub.app.dto.album.AlbumResponseDto;
import com.peachub.app.entity.Album;
import com.peachub.app.mapper.AlbumMapper;
import com.peachub.app.service.AlbumService;
import com.peachub.app.service.ExternalAlbumService;
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

import java.util.List;

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
    @Autowired
    private ExternalAlbumService externalAlbumService;
    @Autowired
    private AlbumMapper albumMapper;

    @GetMapping("/{id}")
    public String albumPage(@PathVariable Long id, Model model, Authentication authentication) {
        Album album = albumService.getAlbumById(id);

        model.addAttribute("album", album);
        model.addAttribute("reviews", reviewService.findByAlbumId(id));

        boolean isFavorite = false;

        if (authentication != null) {
            isFavorite = favoriteAlbumService.isFavorite(authentication.getName(), album.getId());
        }

        model.addAttribute("isFavorite", isFavorite);
        return "album/album";
    }

    @GetMapping
    public String albumsPage(
            @RequestParam(required = false)
            String title,
            @RequestParam(required = false)
            String artist,
            @RequestParam(required = false)
            String genre,
            Model model) {
        if (title != null && !title.isBlank()) {
            model.addAttribute("albums", albumService.searchByTitle(title));
        } else if (artist != null && !artist.isBlank()) {
            model.addAttribute("albums", albumService.searchByArtist(artist));
        } else if (genre != null && !genre.isBlank()) {
            model.addAttribute("albums", albumService.searchByGenre(genre));
        } else {model.addAttribute("albums", albumService.getAllAlbums());
        }
        return "album/albums";
    }

    @GetMapping("/search")
    public String searchAlbums(@RequestParam String query, Model model) {
        var externalAlbums = externalAlbumService.searchAlbums(query);
        if (!externalAlbums.isEmpty()) {
            model.addAttribute("albums", externalAlbums);
            model.addAttribute("externalResults", true);

            return "album/albums";
        }
        model.addAttribute("albums", albumService.search(query));
        return "album/albums";
    }

    @GetMapping("/{id}/edit")
    public String editAlbumPage(@PathVariable Long id, Model model) {

        Album album = albumService.getAlbumById(id);

        AlbumForm form = albumMapper.toForm(album);

        model.addAttribute("albumForm", form);
        model.addAttribute("albumId", id);

        return "album/edit-album";
    }

    @PostMapping("/{id}/edit")
    public String updateAlbum(@PathVariable Long id, @Valid AlbumForm albumForm, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "album/edit-album";
        }

        albumService.updateAlbum(id, albumForm);

        return "redirect:/albums";
    }

    @PostMapping("/{id}/delete")
    public String deleteAlbum(@PathVariable Long id) {

        albumService.deleteAlbum(id);

        return "redirect:/albums";
    }

    @GetMapping("/top")
    public String topAlbumsPage(@RequestParam(required = false) Double minRating, Model model) {

        if (minRating != null) {
            model.addAttribute(
                    "topAlbums",
                    reviewService.getTopAlbumsWithMinRating(minRating)
            );

        } else {
            model.addAttribute("topAlbums", reviewService.getTopAlbums());
        }

        model.addAttribute("minRating", minRating);

        return "album/top-albums";
    }
    @PostMapping("/import")
    public String importAlbum(
            @RequestParam String musicBrainzId,
            @RequestParam String title,
            @RequestParam String artist,
            @RequestParam Integer releaseYear,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) String coverUrl
    ){

        Album existingAlbum = albumService.findByMusicBrainzId(musicBrainzId);

        if (existingAlbum != null) {
            return "redirect:/albums/" + existingAlbum.getId();
        }

        Album album = new Album();

        album.setMusicBrainzId(musicBrainzId);
        album.setTitle(title);
        album.setArtist(artist);
        album.setReleaseYear(releaseYear);
        album.setGenre(genre);
        album.setCoverUrl(coverUrl);

        Album savedAlbum = albumService.save(album);

        return "redirect:/albums/" + savedAlbum.getId();
    }
}