package com.peachub.app.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public String handleUserNotFound(UserNotFoundException ex, Model model) {
        model.addAttribute("error", ex.getMessage());

        return "error";
    }

    @ExceptionHandler(AlbumNotFoundException.class)
    public String handleAlbumNotFound(AlbumNotFoundException ex, Model model) {
        model.addAttribute("error", ex.getMessage());

        return "error";
    }

    @ExceptionHandler(ReviewNotFoundException.class)
    public String handleReviewNotFound(ReviewNotFoundException ex, Model model) {
        model.addAttribute("error", ex.getMessage());

        return "error";
    }

    @ExceptionHandler(CommentNotFoundException.class)
    public String handleCommentNotFound(CommentNotFoundException ex, Model model) {
        model.addAttribute("error", ex.getMessage());

        return "error";
    }

    @ExceptionHandler(FavoriteAlbumNotFoundException.class)
    public String handleFavoriteAlbumNotFound(FavoriteAlbumNotFoundException ex, Model model) {
        model.addAttribute("error", ex.getMessage());

        return "error";
    }

    @ExceptionHandler(Exception.class)
    public String handleUnknownException(Exception ex, Model model) {
        log.error("Необработанная ошибка", ex);
        model.addAttribute("error", "Something went wrong");

        return "error";
    }

    @ExceptionHandler(AccessDeniedException.class)
    public String handleAccessDenied(AccessDeniedException ex, Model model) {
        model.addAttribute("error", ex.getMessage());
        return "error";
    }

    @ExceptionHandler(ExternalApiException.class)
    public String handleExternalApiException(ExternalApiException exception, Model model) {
        model.addAttribute("errorMessage", exception.getMessage());

        return "error";
    }
}