package com.media.social.service;

import com.media.social.model.Post;

import java.util.List;

public interface PostService {
    void updatePost(Post post, Long postId);

    Post getPost(Long postId);

    List<Post> getUserPosts();

    List<Post> getFeedPosts();

    void deletePost(Long postId);

    void createPost(Post post);
}
