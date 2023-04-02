package com.media.social.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Objects;

import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@Entity
@Builder
@Table(name = "likes")
@NoArgsConstructor
@AllArgsConstructor
public class Like {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long likeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ElementCollection
    private List<Long> users;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Like like = (Like) o;
        return Objects.equals(likeId, like.likeId) && Objects.equals(post, like.post) && Objects.equals(users, like.users);
    }

    @Override
    public int hashCode() {
        return Objects.hash(likeId, post, users);
    }
}
