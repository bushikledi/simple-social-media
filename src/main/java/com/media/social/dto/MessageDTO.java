package com.media.social.dto;

import com.media.social.model.Message;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDTO {
    @Column(name = "sender_id")
    private Long senderId;

    @Column(name = "receiver_id")
    private Long receiverId;

    @Column(name = "message_text")
    private String messageText;

    @Column(name = "sent")

    private Instant sent;

    @Column(name = "message_read")
    private boolean messageRead;

    public MessageDTO messageToMessageDTO(Message message) {
        this.senderId = message.getSender().getUserId();
        this.receiverId = message.getReceiver().getUserId();
        this.messageText = message.getMessageText();
        this.sent = message.getSent();
        this.messageRead = message.isMessageRead();
        return this;
    }
}
