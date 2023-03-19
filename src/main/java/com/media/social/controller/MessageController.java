package com.media.social.controller;

import com.media.social.dto.MessageDTO;
import com.media.social.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user/message")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @PostMapping(value = "/{friend_id}", params = "content")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> sendMessage(@RequestParam(name = "content") String content,
                                            @PathVariable(name = "friend_id") Long friendId) {
        messageService.sendMessage(content, friendId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/chat/{friend_id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<MessageDTO>> getFriendMessages(@PathVariable(name = "friend_id") Long friendId) {
        return ResponseEntity.ok(messageService.getFriendMessages(friendId));
    }
}
