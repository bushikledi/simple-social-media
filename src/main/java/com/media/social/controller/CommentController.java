package com.media.social.controller;

import com.media.social.dto.CommentDTO;
import com.media.social.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/user/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping(value = "new/{postId}", params = "comment")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> createComment(@RequestParam String comment, @PathVariable Long postId) {
        commentService.save(postId, comment);
        return new ResponseEntity<>(CREATED);
    }

    @GetMapping("all/{postId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<CommentDTO>> getAllCommentsForPost(@PathVariable Long postId) {
        return ResponseEntity.status(OK)
                .body(commentService.getAllCommentsForPost(postId));
    }

    @GetMapping("user/{userId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<CommentDTO>> getAllCommentsForUser(@PathVariable Long userId) {
        return ResponseEntity.status(OK)
                .body(commentService.getAllCommentsForUser(userId));
    }

}
