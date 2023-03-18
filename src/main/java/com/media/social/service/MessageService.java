package com.media.social.service;

import com.media.social.model.Message;
import com.media.social.model.User;

import java.util.List;

public interface MessageService {
    void sendMessage(String content, Long friendId);

    List<Message> getFriendMessages(Long friendId);
}