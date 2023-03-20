package com.media.social.service;

import com.media.social.dto.MessageDTO;

import java.util.List;

public interface MessageService {
    void sendMessage(String content, Long friendId);

    List<MessageDTO> getFriendMessages(Long friendId);
}
