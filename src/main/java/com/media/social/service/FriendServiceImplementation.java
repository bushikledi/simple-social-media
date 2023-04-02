package com.media.social.service;

import com.media.social.dto.UserDTO;
import com.media.social.model.Friend;
import com.media.social.model.FriendStatus;
import com.media.social.model.User;
import com.media.social.repository.FriendRepository;
import com.media.social.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendServiceImplementation implements FriendService {
    private final FriendRepository friendRepository;
    private final UserRepository userRepository;
    private final AuthenticationService authenticationService;

    @Override
    @Transactional
    public void requestFriend(Long friendId) {
        User user = authenticationService.getAuthenticatedUser();
        User friend = userRepository.findById(friendId)
                .orElseThrow(() -> new RuntimeException("Cannot find friend"));
        try {
            if (friendRepository.findFriendsByUserAndFriendOrUserAndFriend(user, friend, friend, user) == null) {
                friendRepository.save(Friend.builder()
                        .friend(friend)
                        .friendStatus(FriendStatus.PENDING)
                        .user(user)
                        .friendDate(new Date().toInstant())
                        .build());
            } else throw new RuntimeException();
        } catch (Exception e) {
            throw new RuntimeException("Couldn't request friend!", e);
        }
    }

    @Override
    public void acceptFriend(Long friendId) {
        User user = authenticationService.getAuthenticatedUser();
        try {
            Friend friend = friendRepository.findByUserAndFriendAndFriendStatus(userRepository.findById(friendId)
                            .orElseThrow(() -> new RuntimeException("Couldn't get friend")),
                    user, FriendStatus.PENDING).orElseThrow();
            friend.setFriendStatus(FriendStatus.ACCEPTED);
            friend.setFriendDate(new Date().toInstant());
            friendRepository.save(friend);
        } catch (Exception e) {
            throw new RuntimeException("Couldn't accept friend!", e);
        }
    }

    @Override
    public void deleteFriend(Long friendId) {
        User user = authenticationService.getAuthenticatedUser();
        User friend = userRepository.findById(friendId)
                .orElseThrow(() -> new RuntimeException("Couldn't get friend"));
        try {
            friendRepository.deleteByUserAndFriendOrUserAndFriend(user, friend, friend, user);
        } catch (Exception e) {
            throw new RuntimeException("Couldn't delete friend!", e);
        }

    }

    @Override
    public List<UserDTO> getFriends() {
        User user = authenticationService.getAuthenticatedUser();
        List<User> friends = friendRepository.findByFriendOrUserAndFriendStatus(user, user, FriendStatus.ACCEPTED)
                .stream()
                .map(Friend::getUser).toList();
        if (friends.isEmpty()) {
            return null;
        }
        return friends.stream().map(f -> new UserDTO().userToDTO(f)).toList();
    }

    @Override
    public List<UserDTO> getFriendRequest() {
        User user = authenticationService.getAuthenticatedUser();
        List<User> friends = friendRepository.findByFriendAndFriendStatus(user, FriendStatus.PENDING)
                .stream()
                .map(Friend::getUser).toList();
        return friends.stream().map(f -> new UserDTO().userToDTO(f)).toList();
    }

    @Override
    public UserDTO getFriend(Long friendId) {
        User user = authenticationService.getAuthenticatedUser();
        User friend = userRepository.findById(friendId)
                .orElseThrow(() -> new RuntimeException("Couldn't get friend"));
        return new UserDTO().userToDTO(friendRepository.
                findByUserAndFriendOrUserAndFriendAndFriendStatus(user, friend, user, friend, FriendStatus.ACCEPTED)
                .orElseThrow().getFriend());
    }
}
