package com.peachub.app.service;

import com.peachub.app.entity.Comment;
import com.peachub.app.entity.Review;
import com.peachub.app.entity.User;
import com.peachub.app.repository.CommentRepository;
import com.peachub.app.repository.ReviewRepository;
import com.peachub.app.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    public CommentService(
            CommentRepository commentRepository,
            ReviewRepository reviewRepository,
            UserRepository userRepository
    ) {
        this.commentRepository = commentRepository;
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
    }

    public void save(
            Comment comment,
            Long reviewId,
            String email
    ) {

        Review review = reviewRepository
                .findById(reviewId)
                .orElseThrow();

        User user = userRepository
                .findByEmail(email)
                .orElseThrow();

        comment.setReview(review);
        comment.setUser(user);

        commentRepository.save(comment);
    }

    public List<Comment> findByReviewId(Long reviewId) {
        return commentRepository.findByReviewId(reviewId);
    }
}