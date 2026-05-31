package com.peachub.app.controller.web;

import com.peachub.app.entity.Comment;
import com.peachub.app.repository.CommentRepository;
import com.peachub.app.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/albums/{albumId}/reviews/{reviewId}/comments")
@AllArgsConstructor
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private CommentRepository commentRepository;
    @GetMapping("/new")
    public String createCommentPage(
            @PathVariable Long albumId,
            @PathVariable Long reviewId,
            Model model
    ) {

        model.addAttribute("comment", new Comment());

        model.addAttribute("albumId", albumId);
        model.addAttribute("reviewId", reviewId);

        return "comment/create-comment";
    }
    @PostMapping
    public String createComment(
            @PathVariable Long albumId,
            @PathVariable Long reviewId,
            @ModelAttribute Comment comment,
            Authentication authentication
    ) {

        commentService.createComment(comment, reviewId, authentication.getName());
        return "redirect:/albums/" +
                albumId +
                "/reviews/" +
                reviewId;
    }

    @PostMapping("/{commentId}/edit")
    public String updateComment(
            @PathVariable Long albumId,
            @PathVariable Long reviewId,
            @PathVariable Long commentId,
            @RequestParam String content,
            Authentication authentication
    ) {

        commentService.updateComment(commentId, content, authentication.getName());
        return "redirect:/albums/" +
                albumId +
                "/reviews/" +
                reviewId;
    }

    @PostMapping("/{commentId}/delete")
    public String deleteComment(
            @PathVariable Long albumId,
            @PathVariable Long reviewId,
            @PathVariable Long commentId,
            Authentication authentication
    ) {

        commentService.deleteComment(commentId, authentication.getName());
        return "redirect:/albums/" +
                albumId +
                "/reviews/" +
                reviewId;
    }
}