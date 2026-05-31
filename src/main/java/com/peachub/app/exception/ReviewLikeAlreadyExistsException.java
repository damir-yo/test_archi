package com.peachub.app.exception;

public class ReviewLikeAlreadyExistsException
        extends RuntimeException {
    public ReviewLikeAlreadyExistsException(Long reviewId) {
        super("Review already liked");
    }
}