package com.media.social.repository;

import com.media.social.model.Comment;
import com.media.social.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByPostPostId(Long postId);

    void deleteAllByUser(User user);

    void deleteAllByPostPostId(Long postId);

    List<Comment> findAllByUserUserId(Long userId);
}
