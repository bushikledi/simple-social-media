package com.media.social.service;

import com.media.social.dto.CommentDTO;

import java.util.List;

public interface CommentService {
    void save(Long postId, String comment);

    List<CommentDTO> getAllCommentsForPost(Long postId);

    List<CommentDTO> getAllCommentsForUser(Long userId);
}
