package com.peachub.app.service;

import com.peachub.app.dto.AlbumRatingDto;
import com.peachub.app.entity.Review;
import com.peachub.app.entity.User;
import com.peachub.app.repository.ReviewRepository;
import com.peachub.app.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
    }

    public List<Review> findByAlbumId(Long albumId) {
        return reviewRepository.findByAlbumId(albumId);
    }

    public void save(Review review, String email) {

        User user = userRepository
                .findByEmail(email)
                .orElseThrow();

        review.setUser(user);

        reviewRepository.save(review);
    }

    public Review getById(Long id) {

        return reviewRepository
                .findById(id)
                .orElseThrow();
    }

    public void updateReview(
            Long reviewId,
            Review updatedReview,
            String email
    ) {

        Review review = reviewRepository
                .findById(reviewId)
                .orElseThrow();

        if (!review.getUser().getEmail().equals(email)) {
            throw new RuntimeException("Access denied");
        }

        review.setTitle(updatedReview.getTitle());
        review.setContent(updatedReview.getContent());
        review.setRating(updatedReview.getRating());

        reviewRepository.save(review);
    }

    public List<Review> findAll() {
        return reviewRepository.findAll();
    }

    public void deleteReview(
            Long reviewId,
            String email
    ) {

        Review review = reviewRepository
                .findById(reviewId)
                .orElseThrow();

        if (!review.getUser().getEmail().equals(email)) {
            throw new RuntimeException("Access denied");
        }

        reviewRepository.delete(review);
    }
    public List<Review> findByUserId(Long userId) {

        return reviewRepository.findByUserId(userId);
    }

    public List<AlbumRatingDto> getTopAlbums() {

        return reviewRepository.getTopAlbums();
    }
}