package com.peachub.app.controller;

import com.peachub.app.service.AlbumService;
import com.peachub.app.service.ReviewService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final AlbumService albumService;
    private final ReviewService reviewService;

    public HomeController(
            AlbumService albumService,
            ReviewService reviewService
    ) {
        this.albumService = albumService;
        this.reviewService = reviewService;
    }

    @GetMapping("/")
    public String homePage(Model model) {

        model.addAttribute(
                "albums",
                albumService.getAllAlbums()
        );

        model.addAttribute(
                "topAlbums",
                reviewService.getTopAlbums()
        );

        return "index";
    }
}