package com.media.social.service;

import com.media.social.dto.UserDTO;
import com.media.social.model.User;

public interface UserService {
    void updateUser(UserDTO userDTO);

    void deleteUser();

    void saveUser(User user);

    UserDTO getUser();
}
