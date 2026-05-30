package com.peachub.app.repository;

import com.peachub.app.entity.ReviewLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewLikeRepository
        extends JpaRepository<ReviewLike, Long> {

    Optional<ReviewLike> findByUserIdAndReviewId(
            Long userId,
            Long reviewId
    );

    int countByReviewId(Long reviewId);
}