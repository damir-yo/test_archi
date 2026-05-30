package com.peachub.app.service;

import com.peachub.app.entity.Review;
import com.peachub.app.entity.ReviewLike;
import com.peachub.app.entity.User;
import com.peachub.app.repository.ReviewLikeRepository;
import com.peachub.app.repository.ReviewRepository;
import com.peachub.app.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class ReviewLikeService {

    private final ReviewLikeRepository reviewLikeRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;

    public ReviewLikeService(
            ReviewLikeRepository reviewLikeRepository,
            UserRepository userRepository,
            ReviewRepository reviewRepository
    ) {
        this.reviewLikeRepository = reviewLikeRepository;
        this.userRepository = userRepository;
        this.reviewRepository = reviewRepository;
    }

    public void likeReview(
            Long reviewId,
            String email
    ) {

        User user = userRepository
                .findByEmail(email)
                .orElseThrow();

        Review review = reviewRepository
                .findById(reviewId)
                .orElseThrow();

        boolean alreadyLiked =
                reviewLikeRepository
                        .findByUserIdAndReviewId(
                                user.getId(),
                                reviewId
                        )
                        .isPresent();

        if (alreadyLiked) {
            return;
        }

        ReviewLike like = new ReviewLike();

        like.setUser(user);
        like.setReview(review);

        reviewLikeRepository.save(like);
    }

    public void unlikeReview(
            Long reviewId,
            String email
    ) {

        User user = userRepository
                .findByEmail(email)
                .orElseThrow();

        ReviewLike like =
                reviewLikeRepository
                        .findByUserIdAndReviewId(
                                user.getId(),
                                reviewId
                        )
                        .orElseThrow();

        reviewLikeRepository.delete(like);
    }

    public int getLikesCount(Long reviewId) {

        return reviewLikeRepository
                .countByReviewId(reviewId);
    }
}