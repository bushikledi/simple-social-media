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
    List<Friend> findByFriendOrUserAndFriendStatus(User friend, User user, FriendStatus friendStatus);

    List<Friend> findByUserAndFriendStatus(User user, FriendStatus friendStatus);

    Friend findFriendsByUserAndFriendOrUserAndFriend(User user, User friend, User user2, User friend2);

    List<Friend> findByFriendAndFriendStatus(User friend, FriendStatus friendStatus);

    Optional<Friend> findByUserAndFriendAndFriendStatus(User user, User friend, FriendStatus friendStatus);

    Optional<Friend> findByUserAndFriendOrUserAndFriendAndFriendStatus(User user, User friend,
                                                                       User user2, User friend2,
                                                                       FriendStatus friendStatus);

    void deleteAllByUserOrFriend(User user, User friend);

    void deleteByUserAndFriendOrUserAndFriend(User user, User friend, User user2, User friend2);
}
