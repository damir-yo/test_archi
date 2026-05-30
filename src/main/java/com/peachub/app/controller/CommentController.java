package com.peachub.app.controller;

import com.peachub.app.entity.Comment;
import com.peachub.app.service.CommentService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/albums/{albumId}/reviews/{reviewId}/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/new")
    public String createCommentPage(
            @PathVariable Long albumId,
            @PathVariable Long reviewId,
            Model model
    ) {

        model.addAttribute("comment", new Comment());

        model.addAttribute("albumId", albumId);
        model.addAttribute("reviewId", reviewId);

        return "create-comment";
    }

    @PostMapping
    public String createComment(
            @PathVariable Long albumId,
            @PathVariable Long reviewId,
            @ModelAttribute Comment comment,
            Authentication authentication
    ) {

        commentService.save(
                comment,
                reviewId,
                authentication.getName()
        );

        return "redirect:/albums/" +
                albumId +
                "/reviews/" +
                reviewId;
    }
}