package com.media.social.controller;


import com.media.social.dto.PostDTO;
import com.media.social.model.Post;
import com.media.social.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping("create")
    public ResponseEntity<Void> createPost(@RequestBody Post post) {
        postService.createPost(post);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/like/{post_id}")
    public ResponseEntity<Void> setLike(@PathVariable("post_id") Long postId) {
        postService.setLike(postId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("update/{post_id}")
    public ResponseEntity<Void> updatePost(@RequestBody Post post,
                                           @PathVariable(name = "post_id") Long postId) {
        postService.updatePost(post, postId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("{post_id}")
    public ResponseEntity<PostDTO> getPost(@PathVariable(name = "post_id") Long postId) {
        return ResponseEntity.ok(postService.getPost(postId));
    }


    @GetMapping("all")
    public ResponseEntity<List<PostDTO>> getAllPosts() {
        return ResponseEntity.ok(postService.getUserPosts());
    }

    @GetMapping("feed")
    public ResponseEntity<List<PostDTO>> getFeedPosts() {
        return ResponseEntity.ok(postService.getFeedPosts());
    }

    @DeleteMapping("delete/{post_id}")
    public ResponseEntity<Void> deletePost(@PathVariable(name = "post_id") Long postId) {
        postService.deletePost(postId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
