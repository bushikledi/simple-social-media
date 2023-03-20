package com.media.social.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.time.Instant;
import java.util.Objects;

import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@Entity
@Builder
@Table(name = "friends")
@NoArgsConstructor
@AllArgsConstructor
public class Friend {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long friendshipId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "friend_id", referencedColumnName = "user_id")
    @ToString.Exclude
    private User friend;

    @Column(name = "friendship_status")
    @Enumerated(value = EnumType.STRING)
    private FriendStatus friendStatus;

    @Column(name = "friend_date")
    private Instant friendDate;

    @Override
    public String toString() {
        return "Friend{" +
                "friendshipId=" + friendshipId +
                ", user=" + user +
                ", friend=" + friend +
                ", friendStatus=" + friendStatus +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Friend friend = (Friend) o;
        return friendshipId != null && Objects.equals(friendshipId, friend.friendshipId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
