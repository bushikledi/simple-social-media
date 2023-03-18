package com.media.social.repository;

import com.media.social.model.Post;
import com.media.social.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findByPostIdAndUser(Long postId, User user);

    Optional<List<Post>> findByUserOrderByPostCreated(User user);

    void deleteByPostIdAndUser(Long postId, User user);
}
