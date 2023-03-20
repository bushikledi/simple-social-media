package com.media.social.service;

import com.media.social.dto.UserDTO;

import java.util.List;

public interface FriendService {
    void requestFriend(Long friendId);

    void acceptFriend(Long friendId);

    void deleteFriend(Long friendId);

    List<UserDTO> getFriends();

    UserDTO getFriend(Long friendId);

    List<UserDTO> getFriendRequest();
}
