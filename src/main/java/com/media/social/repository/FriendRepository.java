package com.media.social.repository;

import com.media.social.model.Friend;
import com.media.social.model.FriendStatus;
import com.media.social.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {
    List<Friend> findByFriendAndFriendStatus(User user, FriendStatus friendStatus);

    List<Friend> findFriendsByUserAndFriend(User user, User friend);

    Optional<Friend> findByUserAndFriendAndFriendStatus(User user, User friend, FriendStatus friendStatus);

    void deleteByUserAndFriend(User user, User friend);
}
