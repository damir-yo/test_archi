package com.peachub.app.service;

import com.peachub.app.dto.album.AlbumRatingDto;
import com.peachub.app.entity.Review;
import com.peachub.app.entity.User;
import com.peachub.app.exception.AccessDeniedException;
import com.peachub.app.exception.ReviewNotFoundException;
import com.peachub.app.repository.ReviewRepository;
import com.peachub.app.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class ReviewService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ReviewRepository reviewRepository;

    public List<Review> findByAlbumId(Long albumId) {
        return reviewRepository.findByAlbumId(albumId);
    }
    @CacheEvict(value = "topAlbums", allEntries = true)
    public void createReview(Review review, String email) {
        log.info("Создание отзыва пользователем {}", email);
        User user = userRepository
                .findByEmail(email)
                .orElseThrow();

        review.setUser(user);

        reviewRepository.save(review);
        log.info("Отзыв сохранён, id={}", reviewRepository.save(review).getId());
    }

    public Review getById(Long id) {

        log.info("Поиск отзыва id={}", id);

        return reviewRepository
                .findById(id)
                .orElseThrow(() -> {

                    log.warn("Отзыв id={} не найден", id);

                    return new ReviewNotFoundException(id);
                });
    }
    @CacheEvict(value = "topAlbums", allEntries = true)
    public void updateReview(
            Long reviewId,
            Review updatedReview,
            String email
    ) {
        log.info(
                "Пользователь {} пытается изменить отзыв {}",
                email,
                reviewId
        );
        Review review = reviewRepository
                .findById(reviewId)
                .orElseThrow(() ->
                        new ReviewNotFoundException(reviewId)
                );

        if (!review.getUser().getEmail().equals(email)) {
            throw new AccessDeniedException("You can edit only your own comments");
        }

        review.setTitle(updatedReview.getTitle());
        review.setContent(updatedReview.getContent());
        review.setRating(updatedReview.getRating());

        reviewRepository.save(review);
        log.info("Отзыв {} обновлён", reviewId);
    }

    public List<Review> findAll() {
        return reviewRepository.findAll();
    }
    @CacheEvict(value = "topAlbums", allEntries = true)
    public void deleteReview(
            Long reviewId,
            String email
    ) {
        log.info(
                "Пользователь {} удаляет отзыв {}",
                email,
                reviewId
        );
        Review review = reviewRepository
                .findById(reviewId)
                .orElseThrow();

        if (!review.getUser().getEmail().equals(email)) {
            throw new AccessDeniedException("You can edit only your own comments");
        }

        reviewRepository.delete(review);
        log.info(
                "Пользователь {} удаляет отзыв {}",
                email,
                reviewId
        );
    }
    public List<Review> findByUserId(Long userId) {

        return reviewRepository.findByUserId(userId);
    }
    @Cacheable("topAlbums")
    public List<AlbumRatingDto> getTopAlbums() {
        log.info("Получение списка топ альбомов");
        return reviewRepository.getTopAlbums();
    }
    public List<AlbumRatingDto> getTopAlbumsWithMinRating(
            Double minRating
    ) {
        return reviewRepository
                .getTopAlbumsWithMinRating(minRating);
    }
}