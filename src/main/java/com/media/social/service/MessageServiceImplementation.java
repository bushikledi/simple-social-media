package com.media.social.service;

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
    public List<Message> getFriendMessages(Long friendId) {
        User user = authenticationService.getAuthenticatedUser();
        List<Message> readEqualsFalse = messageRepository
                .findBySenderAndReceiverAndMessageReadEquals(user,
                        userRepository.findById(friendId).orElse(null), false);
        if (readEqualsFalse != null) {
            for (Message message : readEqualsFalse) {
                message.setMessageRead(true);
            }
            messageRepository.saveAll(readEqualsFalse);
        }
        return messageRepository.findBySenderAndReceiver(user, userRepository.findById(friendId)
                .orElseThrow(() -> new RuntimeException("Cannot get messages")));
    }
}
