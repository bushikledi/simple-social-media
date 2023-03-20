package com.media.social.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.time.Instant;
import java.util.Objects;

import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@Builder
@Entity
@Table(name = "messages")
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long messageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", referencedColumnName = "user_id")
    @ToString.Exclude
    private User sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id", referencedColumnName = "user_id")
    @ToString.Exclude
    private User receiver;

    @Column(name = "message_text")
    private String messageText;

    @Column(name = "sent")
    private Instant sent;

    @Column(name = "message_read")
    private boolean messageRead;

    @Override
    public String toString() {
        return "Message{" +
                "messageId=" + messageId +
                ", sender=" + sender +
                ", receiver=" + receiver +
                ", messageText='" + messageText + '\'' +
                ", sent=" + sent +
                ", messageRead=" + messageRead +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Message message = (Message) o;
        return messageId != null && Objects.equals(messageId, message.messageId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
