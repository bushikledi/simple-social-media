package com.media.social.dto;

import com.media.social.model.Comment;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {
    private Long id;
    private Long postId;
    private Instant commentDate;
    @NotBlank
    private String comment;
    private String firstname;
    private String lastname;

    public CommentDTO commentToDTO(Comment comment) {
        this.id = comment.getCommentId();
        this.postId = comment.getPost().getPostId();
        this.commentDate = comment.getCommentDate();
        this.comment = comment.getComment();
        this.firstname = comment.getUser().getFirstname();
        this.lastname = comment.getUser().getLastname();
        return this;
    }
}
