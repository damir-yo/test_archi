package com.peachub.app.controller;

import com.peachub.app.service.ReviewLikeService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/albums/{albumId}/reviews/{reviewId}/likes")
public class ReviewLikeController {

    private final ReviewLikeService reviewLikeService;

    public ReviewLikeController(
            ReviewLikeService reviewLikeService
    ) {
        this.reviewLikeService = reviewLikeService;
    }

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