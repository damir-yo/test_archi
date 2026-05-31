package com.peachub.app.service;

import com.peachub.app.entity.Comment;
import com.peachub.app.entity.Review;
import com.peachub.app.entity.User;
import com.peachub.app.exception.AccessDeniedException;
import com.peachub.app.exception.ReviewNotFoundException;
import com.peachub.app.exception.UserNotFoundException;
import com.peachub.app.repository.CommentRepository;
import com.peachub.app.repository.ReviewRepository;
import com.peachub.app.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private UserRepository userRepository;

    public void createComment(Comment comment, Long reviewId, String email) {
        log.info("Пользователь {} создаёт комментарий к отзыву {}", email, reviewId);
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> {
                    log.warn("Отзыв {} не найден при создании комментария", reviewId);
                    return new ReviewNotFoundException(reviewId);
                });

        User user = userRepository.findByEmail(email).orElseThrow(() -> {
                    log.warn("Пользователь {} не найден при создании комментария", email);
                    return new UserNotFoundException(email);
                });

        comment.setReview(review);
        comment.setUser(user);

        if (comment.getContent() == null || comment.getContent().isBlank()) {
            log.warn("Пользователь {} попытался создать пустой комментарий", email);
            throw new IllegalArgumentException("Comment cannot be empty");
        }
        commentRepository.save(comment);
        log.info("Комментарий успешно создан пользователем {}", email);

    }

    public void updateComment(Long commentId, String newContent, String userEmail) {
        log.info("Пользователь {} редактирует комментарий {}", userEmail, commentId);
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> {
                    log.warn("Комментарий {} не найден", commentId);
                    return new EntityNotFoundException(
                            "Comment not found"
                    );
                });
        if (!comment.getUser().getEmail().equals(userEmail)) {
            throw new AccessDeniedException("You can edit only your own comments");
        }

        comment.setContent(newContent);
        commentRepository.save(comment);
        log.info("Комментарий {} успешно обновлён", commentId);
    }

    public void deleteComment(Long commentId, String userEmail) {
        log.info("Пользователь {} удаляет комментарий {}", userEmail, commentId);
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> {
                    log.warn("Комментарий {} не найден", commentId);
                    return new EntityNotFoundException(
                            "Comment not found"
                    );
                });
        if (!comment.getUser().getEmail().equals(userEmail)) {
            throw new AccessDeniedException(
                    "You can delete only your own comments");
        }

        commentRepository.delete(comment);
    }
}