package com.peachub.app.controller;

import com.peachub.app.service.AlbumService;
import com.peachub.app.service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class HomeController {
    @Autowired
    private AlbumService albumService;
    @Autowired
    private ReviewService reviewService;

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