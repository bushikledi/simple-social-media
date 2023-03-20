package com.media.social.repository;

import com.media.social.model.Message;
import com.media.social.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findBySenderAndReceiverOrSenderAndReceiverOrderBySentDesc(User sender, User receiver, User sender2, User receiver2);

    void deleteAllByReceiverOrSender(User receiver, User sender);

    List<Message> findBySenderAndReceiverAndMessageReadEquals(User sender, User receiver, boolean read);
}
