package com.peachub.app.controller;

import com.peachub.app.service.ReviewLikeService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/albums/{albumId}/reviews/{reviewId}/likes")
@AllArgsConstructor
public class ReviewLikeController {
    @Autowired
    private ReviewLikeService reviewLikeService;

    @PostMapping
    public String likeReview(
            @PathVariable Long albumId,
            @PathVariable Long reviewId,
            Authentication authentication
    ) {

        reviewLikeService.likeReview(
                reviewId,
                authentication.getName()
        );

        return "redirect:/albums/" +
                albumId +
                "/reviews/" +
                reviewId;
    }

    @PostMapping("/delete")
    public String unlikeReview(
            @PathVariable Long albumId,
            @PathVariable Long reviewId,
            Authentication authentication
    ) {

        reviewLikeService.unlikeReview(
                reviewId,
                authentication.getName()
        );

        return "redirect:/albums/" +
                albumId +
                "/reviews/" +
                reviewId;
    }
}