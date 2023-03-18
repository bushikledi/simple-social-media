package com.media.social.service;

import com.media.social.model.Friend;
import com.media.social.model.FriendStatus;
import com.media.social.model.Post;
import com.media.social.model.User;
import com.media.social.repository.FriendRepository;
import com.media.social.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostServiceImplementation implements PostService {

    private final PostRepository postRepository;
    private final FriendRepository friendRepository;
    private final AuthenticationService authenticationService;

    @Override
    public void updatePost(Post post, Long postId) {
        try {
            Post post1 = getPost(postId);
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
    public Post getPost(Long postId) {
        User user = authenticationService.getAuthenticatedUser();
        return postRepository.findByPostIdAndUser(postId, user)
                .orElseThrow(() -> new RuntimeException("Post not found"));
    }

    @Override
    public List<Post> getUserPosts() {
        User user = authenticationService.getAuthenticatedUser();
        return postRepository.findByUserOrderByPostCreated(user)
                .orElseThrow(() -> new RuntimeException("Posts not found"));
    }

    @Override
    public List<Post> getFeedPosts() {
        User user = authenticationService.getAuthenticatedUser();
        List<Post> posts = getUserPosts();
        log.info(posts.toString());
        List<User> feedUsers = friendRepository.findByFriendAndFriendStatus(user, FriendStatus.ACCEPTED)
                .stream()
                .map(Friend::getFriend)
                .toList();
        for (User friend : feedUsers) {
            postRepository.findByUserOrderByPostCreated(friend).ifPresent(posts::addAll);
        }
        posts.sort(Comparator.comparing(Post::getPostCreated).reversed());
        return posts;
    }

    @Override
    public void deletePost(Long postId) {
        User user = authenticationService.getAuthenticatedUser();
        try {
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
            post.setLikeCount(0);
            post.setPostCreated(new Date().toInstant());
            postRepository.save(post);
        } catch (Exception e) {
            throw new RuntimeException("Unable to create the post", e);
        }
    }
}
