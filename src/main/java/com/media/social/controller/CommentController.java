package com.media.social.controller;

import com.media.social.dto.CommentDTO;
import com.media.social.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping(value = "new/{postId}", params = "comment")
    public ResponseEntity<Void> createComment(@RequestParam String comment, @PathVariable Long postId) {
        commentService.save(postId, comment);
        return new ResponseEntity<>(CREATED);
    }

    @GetMapping("all/{postId}")
    public ResponseEntity<List<CommentDTO>> getAllCommentsForPost(@PathVariable Long postId) {
        return ResponseEntity.status(OK)
                .body(commentService.getAllCommentsForPost(postId));
    }

    @GetMapping("user/{userId}")
    public ResponseEntity<List<CommentDTO>> getAllCommentsForUser(@PathVariable Long userId) {
        return ResponseEntity.status(OK)
                .body(commentService.getAllCommentsForUser(userId));
    }
}
