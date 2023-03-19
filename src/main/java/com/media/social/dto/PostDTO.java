package com.media.social.dto;

import com.media.social.model.Post;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {
    @Column(name = "userId", nullable = false)
    private Long userId;
    @Column(name = "content", nullable = false)
    private String content;
    @Column(name = "image_url")
    private String imageUrl;
    @Column(name = "like_count")
    private Integer likeCount;
    @Column(name = "post_created", nullable = false)
    private Instant postCreated;

    public PostDTO postToPostDTO(Post post) {
        this.userId = post.getUser().getUserId();
        this.content = post.getContent();
        this.imageUrl = post.getImageUrl();
        this.likeCount = post.getLikeCount();
        this.postCreated = post.getPostCreated();
        return this;
    }
}
