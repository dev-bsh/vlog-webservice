package com.maen.vlogwebserviceserver.web;

import com.maen.vlogwebserviceserver.service.posts.PostsService;
import com.maen.vlogwebserviceserver.service.user.FollowsService;
import com.maen.vlogwebserviceserver.service.user.UserService;
import com.maen.vlogwebserviceserver.web.dto.UserResponseDto;
import com.maen.vlogwebserviceserver.web.dto.FollowsSaveRequestDto;
import com.maen.vlogwebserviceserver.web.dto.PostsAllResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final FollowsService followsService;
    private final PostsService postsService;
    private final UserService userService;

    @GetMapping("/api/v1/user/{userId}")
    public UserResponseDto getUser(@PathVariable Long userId) {
        return userService.findById(userId);
    }

    @PostMapping("/api/v1/follows")
    public Long saveFollow(@RequestBody FollowsSaveRequestDto requestDto) {
        return followsService.saveFollow(requestDto);
    }

    @DeleteMapping("/api/v1/{userId}/{followTargetId}")
    public void deleteFollow(@PathVariable Long userId, @PathVariable Long followTargetId) {
        followsService.deleteFollow(userId, followTargetId);
    }

    @GetMapping("/api/v1/user/{userId}/follower")
    public List<UserResponseDto> getFollowerList(@PathVariable Long userId) {
        return followsService.findFollowerListByUserId(userId, null);
    }
    @GetMapping("/api/v1/user/{userId}/follower/{lastFollowsId}")
    public List<UserResponseDto> getFollowerList(@PathVariable Long userId, @PathVariable Long lastFollowsId) {
        return followsService.findFollowerListByUserId(userId, lastFollowsId);
    }

    @GetMapping("/api/v1/user/{userId}/following")
    public List<UserResponseDto> getFollowingList(@PathVariable Long userId) {
        return followsService.findFollowingListByUserId(userId, null);
    }
    @GetMapping("/api/v1/user/{userId}/following/{lastFollowsId}")
    public List<UserResponseDto> getFollowingList(@PathVariable Long userId, @PathVariable Long lastFollowsId) {
        return followsService.findFollowingListByUserId(userId, lastFollowsId);
    }

    @GetMapping("/api/v1/user/search/{keyword}/{pageNumber}")
    public List<UserResponseDto> searchUser(@PathVariable String keyword, @PathVariable Integer pageNumber) {
        return userService.searchUser(keyword, pageNumber);
    }




    @GetMapping("/api/v1/user/{userId}/posts")
    public List<PostsAllResponseDto> getUserPosts(@PathVariable Long userId) {
        return postsService.findByUserId(userId, null);
    }

    @GetMapping("/api/v1/user/{userId}/posts/{lastPostId}")
    public List<PostsAllResponseDto> getUserPosts(@PathVariable Long userId, @PathVariable Long lastPostId) {
        return postsService.findByUserId(userId, lastPostId);
    }






}
