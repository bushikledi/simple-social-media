package com.media.social.service;

import com.media.social.dto.MessageDTO;
import com.media.social.model.Message;
import com.media.social.model.User;
import com.media.social.repository.MessageRepository;
import com.media.social.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageServiceImplementation implements MessageService {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final AuthenticationService authenticationService;

    @Override
    public void sendMessage(String content, Long friendId) {
        User user = authenticationService.getAuthenticatedUser();
        try {
            messageRepository.save(Message.builder()
                    .sender(user)
                    .receiver(userRepository.findById(friendId)
                            .orElseThrow(() -> new RuntimeException("Couldn't get friend")))
                    .sent(Instant.now())
                    .messageText(content)
                    .messageRead(false)
                    .build());
        } catch (Exception e) {
            throw new RuntimeException("Couldn't send message", e);
        }
    }

    @Override
    public List<MessageDTO> getFriendMessages(Long friendId) {
        User user = authenticationService.getAuthenticatedUser();
        User friend = userRepository.findById(friendId).orElseThrow(() -> new RuntimeException("Cannot get messages"));
        List<Message> readEqualsFalse = messageRepository
                .findBySenderAndReceiverAndMessageReadEquals(
                        userRepository.findById(friendId).orElse(null),
                        user, false);
        if (readEqualsFalse != null) {
            for (Message message : readEqualsFalse) {
                message.setMessageRead(true);
            }
            messageRepository.saveAll(readEqualsFalse);
        }
        return messageRepository.findBySenderAndReceiverOrSenderAndReceiverOrderBySentDesc(
                        friend, user, user, friend)
                .stream().map(m -> new MessageDTO().messageToMessageDTO(m)).toList();
    }
}
