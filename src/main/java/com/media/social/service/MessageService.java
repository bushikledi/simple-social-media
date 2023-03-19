package com.media.social.service;

import com.media.social.dto.MessageDTO;
import com.media.social.model.Message;
import com.media.social.model.User;

import java.util.List;

public interface MessageService {
    void sendMessage(String content, Long friendId);

    List<MessageDTO> getFriendMessages(Long friendId);
}
