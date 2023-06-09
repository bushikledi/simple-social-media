package com.media.social.controller;

import com.media.social.dto.UserDTO;
import com.media.social.service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/friend")
@RequiredArgsConstructor
public class FriendController {
    private final FriendService friendService;

    @PostMapping("/request/{friend_id}")
    public ResponseEntity<Void> request(@PathVariable(name = "friend_id") Long friendId) {
        friendService.requestFriend(friendId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/accept/{friend_id}")
    public ResponseEntity<Void> acceptFriend(@PathVariable(name = "friend_id") Long friendId) {
        friendService.acceptFriend(friendId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{friend_id}")
    public ResponseEntity<Void> deleteFriend(@PathVariable(name = "friend_id") Long friendId) {
        friendService.deleteFriend(friendId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> getFriends() {
        return ResponseEntity.ok(friendService.getFriends());
    }

    @GetMapping("/requests")
    public ResponseEntity<List<UserDTO>> getFriendRequest() {
        return ResponseEntity.ok(friendService.getFriendRequest());
    }

    @GetMapping("/{friend_id}")
    public ResponseEntity<UserDTO> getFriend(@PathVariable(name = "friend_id") Long friendId) {
        return ResponseEntity.ok(friendService.getFriend(friendId));
    }
}
