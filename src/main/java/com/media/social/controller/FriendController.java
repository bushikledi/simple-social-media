package com.media.social.controller;

import com.media.social.dto.UserDTO;
import com.media.social.service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user/friend")
@RequiredArgsConstructor
public class FriendController {
    private final FriendService friendService;

    @PostMapping("/request/{friend_id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> request(@PathVariable(name = "friend_id") Long friendId) {
        friendService.requestFriend(friendId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/accept/{friend_id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> acceptFriend(@PathVariable(name = "friend_id") Long friendId) {
        friendService.acceptFriend(friendId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{friend_id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> deleteFriend(@PathVariable(name = "friend_id") Long friendId) {
        friendService.deleteFriend(friendId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/all")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<UserDTO>> getFriends() {
        return ResponseEntity.ok(friendService.getFriends());
    }

    @GetMapping("/requests")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<UserDTO>> getFriendRequest() {
        return ResponseEntity.ok(friendService.getFriendRequest());
    }

    @GetMapping("/{friend_id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserDTO> getFriend(@PathVariable(name = "friend_id") Long friendId) {
        return ResponseEntity.ok(friendService.getFriend(friendId));
    }
}
