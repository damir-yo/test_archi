package com.peachub.app.controller.web;

import com.peachub.app.entity.Album;
import com.peachub.app.entity.Comment;
import com.peachub.app.entity.Review;
import com.peachub.app.service.AlbumService;
import com.peachub.app.service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/albums/{albumId}/reviews")
@AllArgsConstructor
public class ReviewController {
    @Autowired
    private AlbumService albumService;
    @Autowired
    private ReviewService reviewService;

    @GetMapping
    public String reviewsPage(@PathVariable Long albumId, Model model) {
        Album album = albumService.getAlbumById(albumId);
        model.addAttribute("album", album);
        model.addAttribute("reviews", reviewService.findByAlbumId(albumId));

        return "reviews";
    }

    @GetMapping("/{reviewId}")
    public String reviewPage(
            @PathVariable Long albumId,
            @PathVariable Long reviewId,
            Model model,
            Authentication authentication
    ) {
        Review review = reviewService.getById(reviewId);

        if (!review.getAlbum().getId().equals(albumId)) {
            return "redirect:/albums";
        }

        boolean isLiked = false;

        if (authentication != null) {
            isLiked = review.getLikes().stream().anyMatch(like ->
                            like.getUser()
                                    .getEmail()
                                    .equals(authentication.getName())
                    );
        }
        boolean isOwner = false;

        if (authentication != null) {

            isOwner = review.getUser().getEmail().equals(authentication.getName());
        }

        model.addAttribute("review", review);
        model.addAttribute("isLiked", isLiked);
        model.addAttribute("isOwner", isOwner);
        model.addAttribute("comment", new Comment());

        return "review/review";
    }

    @GetMapping("/new")
    public String createReviewForm(
            @PathVariable Long albumId,
            Model model
    ) {

        Review review = new Review();
        Album album = albumService.getAlbumById(albumId);
        review.setAlbum(album);
        model.addAttribute("review", review);

        return "review/create-review";
    }

    @PostMapping
    public String createReview(@PathVariable Long albumId, @ModelAttribute Review review, Authentication authentication) {

        Album album = albumService.getAlbumById(albumId);

        review.setAlbum(album);

        reviewService.createReview(review, authentication.getName());

        return "redirect:/albums/" + albumId;
    }

    @GetMapping("/{reviewId}/edit")
    public String editReviewPage(@PathVariable Long albumId, @PathVariable Long reviewId, Model model) {

        Review review = reviewService.getById(reviewId);

        if (!review.getAlbum().getId().equals(albumId)) {
            return "redirect:/albums";
        }

        model.addAttribute("review", review);

        return "review/edit-review";
    }

    @PostMapping("/{reviewId}/edit")
    public String updateReview(
            @PathVariable Long albumId,
            @PathVariable Long reviewId,
            @ModelAttribute Review updatedReview,
            Authentication authentication
    ) {
        Review review = reviewService.getById(reviewId);

        if (!review.getAlbum().getId().equals(albumId)) {
            return "redirect:/albums";
        }

        reviewService.updateReview(reviewId, updatedReview, authentication.getName());

        return "redirect:/albums/" + albumId;
    }

    @PostMapping("/{reviewId}/delete")
    public String deleteReview(
            @PathVariable Long albumId,
            @PathVariable Long reviewId,
            Authentication authentication
    ) {
        Review review = reviewService.getById(reviewId);
        if (!review.getAlbum().getId().equals(albumId)) {
            return "redirect:/albums";
        }
        reviewService.deleteReview(reviewId, authentication.getName());

        return "redirect:/albums/" + albumId;
    }
}