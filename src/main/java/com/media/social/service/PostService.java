package com.media.social.service;

import com.media.social.dto.PostDTO;
import com.media.social.model.Post;

import java.util.List;

public interface PostService {
    void updatePost(Post post, Long postId);

    PostDTO getPost(Long postId);

    List<PostDTO> getUserPosts();

    List<PostDTO> getFeedPosts();

    void deletePost(Long postId);

    void createPost(Post post);

    void setLike(Long postId);
}
