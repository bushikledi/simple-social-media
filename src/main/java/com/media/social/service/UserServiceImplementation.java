package com.media.social.service;

import com.media.social.configuration.PasswordEncoder;
import com.media.social.dto.UserDTO;
import com.media.social.model.Role;
import com.media.social.model.User;
import com.media.social.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class UserServiceImplementation implements UserService {

    private final AuthenticationService authenticationService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final MessageRepository messageRepository;
    private final FriendRepository friendRepository;
    private final CommentRepository commentRepository;

    @Override
    public void updateUser(UserDTO userDTO) {
        User user = authenticationService.getAuthenticatedUser();
        try {
            if (!userDTO.getAbout().isEmpty())
                user.setAbout(userDTO.getAbout());
            if (!userDTO.getFirstname().isEmpty())
                user.setAbout(userDTO.getFirstname());
            if (!userDTO.getLastname().isEmpty())
                user.setAbout(userDTO.getLastname());
            if (!userDTO.getLocation().isEmpty())
                user.setAbout(userDTO.getLocation());
            if (!userDTO.getImageUrl().isEmpty())
                user.setAbout(userDTO.getImageUrl());
            userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException("Error updating user", e);
        }
    }

    @Override
    @Transactional
    public void deleteUser() {
        User user = authenticationService.getAuthenticatedUser();
        try {
            postRepository.deleteAllByUser(user);
            commentRepository.deleteAllByUser(user);
            friendRepository.deleteAllByUserOrFriend(user, user);
            messageRepository.deleteAllByReceiverOrSender(user, user);
            userRepository.deleteById(user.getUserId());
        } catch (Exception e) {
            throw new RuntimeException("Error deleting user", e);
        }
    }

    @Override
    public void saveUser(User user) {
        try {
            user.setPassword(passwordEncoder.encoder().encode(user.getPassword()));
            user.setRole(Role.USER);
            user.setUserCreated(new Date().toInstant());
            userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException("Couldn't save user", e);
        }
    }

    @Override
    public UserDTO getUser() {
        UserDTO userDTO = new UserDTO();
        return userDTO.userToDTO(authenticationService.getAuthenticatedUser());

    }
}
