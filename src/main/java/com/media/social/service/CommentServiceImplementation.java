package com.media.social.service;

import com.media.social.dto.CommentDTO;
import com.media.social.model.Comment;
import com.media.social.model.User;
import com.media.social.repository.CommentRepository;
import com.media.social.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImplementation implements CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final AuthenticationService authenticationService;


    @Override
    public void save(Long postId, String comment) {
        User user = authenticationService.getAuthenticatedUser();
        try {
            commentRepository.save(Comment.builder()
                    .comment(comment)
                    .commentDate(new Date().toInstant())
                    .post(postRepository.findById(postId)
                            .orElseThrow(()->new RuntimeException("Post not found!")))
                    .user(user)
                    .build());
        } catch (Exception e) {
            throw new RuntimeException("Couldn't save comment!");
        }
    }

    @Override
    public List<CommentDTO> getAllCommentsForPost(Long postId) {
        CommentDTO commentDTO = new CommentDTO();
        return commentRepository.findAllByPostPostId(postId).stream()
                .map(commentDTO::commentToDTO)
                .toList();
    }

    @Override
    public List<CommentDTO> getAllCommentsForUser(Long userId) {
        CommentDTO commentDTO = new CommentDTO();
        return commentRepository.findAllByUserUserId(userId).stream()
                .map(commentDTO::commentToDTO)
                .toList();
    }
}
