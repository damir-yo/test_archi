package com.peachub.app.controller;

import com.peachub.app.entity.User;
import com.peachub.app.service.FavoriteAlbumService;
import com.peachub.app.service.ReviewService;
import com.peachub.app.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private FavoriteAlbumService favoriteAlbumService;

    @GetMapping("/me")
    public String myProfile(
            Authentication authentication
    ) {

        User user = userService.findByEmail(authentication.getName());

        return "redirect:/users/" + user.getId();
    }

    @GetMapping("/{id}")
    public String profilePage(
            @PathVariable Long id,
            Model model
    ) {

        User user = userService.getById(id);

        model.addAttribute("profileUser", user);

        model.addAttribute(
                "reviews",
                reviewService.findByUserId(id)
        );

        model.addAttribute(
                "favorites",
                favoriteAlbumService.getUserFavorites(id)
        );

        return "profile";
    }
}