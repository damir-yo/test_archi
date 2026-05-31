package com.peachub.app.service;

import com.peachub.app.entity.Review;
import com.peachub.app.entity.ReviewLike;
import com.peachub.app.entity.User;
import com.peachub.app.exception.ReviewLikeAlreadyExistsException;
import com.peachub.app.exception.ReviewNotFoundException;
import com.peachub.app.exception.UserNotFoundException;
import com.peachub.app.repository.ReviewLikeRepository;
import com.peachub.app.repository.ReviewRepository;
import com.peachub.app.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class ReviewLikeService {
    @Autowired
    private ReviewLikeRepository reviewLikeRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ReviewRepository reviewRepository;

    public void likeReview(Long reviewId, String email) {
        log.info("Пользователь {} ставит лайк отзыву {}", email, reviewId);

        User user = userRepository.findByEmail(email).orElseThrow(() -> {
                    log.warn("Пользователь {} не найден", email);
                    return new UserNotFoundException(email);
                });

        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> {
                    log.warn("Отзыв {} не найден", reviewId);
                    return new ReviewNotFoundException(reviewId);
                });
        boolean alreadyLiked = reviewLikeRepository.findByUserIdAndReviewId(user.getId(), reviewId).isPresent();

        if (alreadyLiked) {
            throw new ReviewLikeAlreadyExistsException(reviewId);
        }

        ReviewLike like = new ReviewLike();

        like.setUser(user);
        like.setReview(review);

        reviewLikeRepository.save(like);
        log.info("Лайк успешно сохранён. Пользователь={}, отзыв={}", email, reviewId);
    }

    public void unlikeReview(Long reviewId, String email) {
        log.info("Пользователь {} снимает лайк с отзыва {}", email, reviewId);
        User user = userRepository.findByEmail(email).orElseThrow(() -> {log.warn("Пользователь {} не найден", email);
                    return new UserNotFoundException(email);
                });
        ReviewLike like = reviewLikeRepository
                        .findByUserIdAndReviewId(
                                user.getId(),
                                reviewId
                        ).orElseThrow(() ->
                                new ReviewLikeAlreadyExistsException(reviewId));

        reviewLikeRepository.delete(like);
        log.info("Лайк удалён. Пользователь={}, отзыв={}", email, reviewId);
    }

    public int getLikesCount(Long reviewId) {
        return reviewLikeRepository.countByReviewId(reviewId);
    }
}