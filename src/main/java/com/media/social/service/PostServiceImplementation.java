package com.media.social.service;

import com.media.social.dto.PostDTO;
import com.media.social.model.*;
import com.media.social.repository.CommentRepository;
import com.media.social.repository.FriendRepository;
import com.media.social.repository.LikeRepository;
import com.media.social.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImplementation implements PostService {
    private final PostRepository postRepository;
    private final FriendRepository friendRepository;
    private final AuthenticationService authenticationService;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;

    @Override
    public void updatePost(Post post, Long postId) {
        User user = authenticationService.getAuthenticatedUser();
        try {
            Post post1 = postRepository.findByPostIdAndUser(postId, user).orElse(null);
            if (post1 == null)
                throw new NullPointerException();
            if (!post.getContent().isEmpty())
                post1.setContent(post.getContent());
            if (!post.getImageUrl().isEmpty())
                post1.setImageUrl(post.getImageUrl());
            postRepository.save(post1);
        } catch (Exception e) {
            throw new RuntimeException("Error updating Post", e);
        }

    }

    @Override
    public PostDTO getPost(Long postId) {
        User user = authenticationService.getAuthenticatedUser();
        return postRepository.findByPostIdAndUser(postId, user)
                .map(p -> new PostDTO().postToPostDTO(p))
                .orElseThrow(() -> new RuntimeException("Post not found"));
    }

    private List<PostDTO> getUserPosts(User user, Long id) {
        List<Post> posts = postRepository.findByUserOrderByPostCreated(user)
                .orElseThrow(() -> new RuntimeException("Posts not found"));
        return posts.stream()
                .map(p -> {
                    PostDTO postDTO = new PostDTO().postToPostDTO(p);
                    Like like = likeRepository.findByPostPostId(p.getPostId())
                            .orElseThrow(() -> new RuntimeException("Likes not found"));
                    postDTO.setLiked(like.getUsers().contains(id));
                    postDTO.setLikeCount(like.getUsers().size());
                    return postDTO;
                })
                .toList();
    }

    @Override
    public List<PostDTO> getUserPosts() {
        User user = authenticationService.getAuthenticatedUser();
        return getUserPosts(user, user.getUserId());
    }

    @Override
    public List<PostDTO> getFeedPosts() {
        User user = authenticationService.getAuthenticatedUser();
        List<PostDTO> posts = new ArrayList<>(getUserPosts());
        List<User> feedUsers = new ArrayList<>(friendRepository.findByFriendAndFriendStatus(user, FriendStatus.ACCEPTED)
                .stream()
                .map(Friend::getUser)
                .toList());
        feedUsers.addAll(friendRepository.findByUserAndFriendStatus(user, FriendStatus.ACCEPTED)
                .stream()
                .map(Friend::getFriend)
                .toList());
        posts.addAll(feedUsers.stream()
                .flatMap(u -> getUserPosts(u, user.getUserId()).stream())
                .toList());
        posts.sort(Comparator.comparing(PostDTO::getPostCreated).reversed());
        return posts;
    }

    @Override
    @Transactional
    public void deletePost(Long postId) {
        User user = authenticationService.getAuthenticatedUser();
        try {
            postRepository.findByPostIdAndUser(postId, user).orElseThrow();
            commentRepository.deleteAllByPostPostId(postId);
            likeRepository.delete(
                    likeRepository.findByPostPostId(postId).orElseThrow()
            );
            postRepository.deleteByPostIdAndUser(postId, user);
        } catch (Exception e) {
            throw new RuntimeException("Unable to delete the post", e);
        }
    }

    @Override
    public void createPost(Post post) {
        User user = authenticationService.getAuthenticatedUser();
        try {
            post.setUser(user);
            post.setPostCreated(new Date().toInstant());
            postRepository.save(post);
            likeRepository.save(Like.builder()
                    .users(null)
                    .post(post)
                    .build());
        } catch (Exception e) {
            throw new RuntimeException("Unable to create the post", e);
        }
    }

    @Override
    public void setLike(Long postId) {
        User user = authenticationService.getAuthenticatedUser();
        Like like = likeRepository.findByPostPostId(postId).orElseThrow(() -> new RuntimeException("Like not found!"));
        if (like.getUsers().contains(user.getUserId())) {
            like.getUsers().remove(user.getUserId());
        } else {
            like.getUsers().add(user.getUserId());
        }
        likeRepository.save(like);
    }
}
