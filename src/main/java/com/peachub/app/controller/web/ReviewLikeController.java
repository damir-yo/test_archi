package com.peachub.app.controller.web;

import com.peachub.app.dto.reviewLike.LikeResponse;
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
        reviewLikeService.likeReview(reviewId, authentication.getName());

        return "redirect:/albums/" + albumId;
    }

    @PostMapping("/delete")
    public String unlikeReview(@PathVariable Long albumId, @PathVariable Long reviewId, Authentication authentication) {
        reviewLikeService.unlikeReview(reviewId, authentication.getName());
        return "redirect:/albums/" + albumId;
    }
    @PostMapping("/ajax")
    @ResponseBody
    public LikeResponse likeReviewAjax(@PathVariable Long reviewId, Authentication authentication
    ) {
        reviewLikeService.likeReview(reviewId, authentication.getName());

        int likesCount = reviewLikeService.getLikesCount(reviewId);
        return new LikeResponse(true, likesCount);
    }

    @PostMapping("/ajax/delete")
    @ResponseBody
    public LikeResponse unlikeReviewAjax(@PathVariable Long reviewId, Authentication authentication) {

        reviewLikeService.unlikeReview(reviewId, authentication.getName());

        int likesCount = reviewLikeService.getLikesCount(reviewId);

        return new LikeResponse(false, likesCount);
    }
}