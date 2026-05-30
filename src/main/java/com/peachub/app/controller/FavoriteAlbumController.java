package com.peachub.app.controller;

import com.peachub.app.entity.User;
import com.peachub.app.service.FavoriteAlbumService;
import com.peachub.app.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/favorites")
@AllArgsConstructor
public class FavoriteAlbumController {
    @Autowired
    private FavoriteAlbumService favoriteAlbumService;
    @Autowired
    private UserService userService;

    @GetMapping
    public String favoritesPage(
            Model model,
            Authentication authentication
    ) {

        User user = userService.findByEmail(authentication.getName());

        model.addAttribute(
                "favorites",
                favoriteAlbumService.getUserFavorites(
                        user.getId()
                )
        );

        return "favorites";
    }

    @PostMapping("/{albumId}")
    public String addToFavorites(
            @PathVariable Long albumId,
            Authentication authentication
    ) {

        favoriteAlbumService.addToFavorites(
                albumId,
                authentication.getName()
        );

        return "redirect:/albums/" + albumId;
    }

    @PostMapping("/{albumId}/delete")
    public String removeFromFavorites(
            @PathVariable Long albumId,
            @RequestParam String source,
            Authentication authentication
    ) {

        favoriteAlbumService.removeFromFavorites(
                albumId,
                authentication.getName()
        );

        if ("favorites".equals(source)) {
            return "redirect:/favorites";
        }

        return "redirect:/albums/" + albumId;
    }
}