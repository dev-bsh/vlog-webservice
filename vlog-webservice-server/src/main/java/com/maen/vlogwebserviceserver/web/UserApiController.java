package com.maen.vlogwebserviceserver.web;

import com.maen.vlogwebserviceserver.service.posts.PostsService;
import com.maen.vlogwebserviceserver.service.posts.TagsService;
import com.maen.vlogwebserviceserver.service.user.FollowsService;
import com.maen.vlogwebserviceserver.service.user.UserService;
import com.maen.vlogwebserviceserver.web.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final FollowsService followsService;
    private final PostsService postsService;
    private final UserService userService;
    private final TagsService tagsService;

    // 사용자 정보 조회
    @GetMapping("/api/v1/user/{userId}")
    public UserResponseDto getUser(@PathVariable Long userId) {
        return userService.findById(userId);
    }
    // 회원 탈퇴
    @DeleteMapping("/api/v1/user/{userId}")
    public Long deleteUser(@PathVariable Long userId) {
        return userService.deleteUser(userId);
    }

    // 팔로우 신청
    @PostMapping("/api/v1/follows")
    public Long saveFollow(@RequestBody FollowsSaveRequestDto requestDto) {
        return followsService.saveFollow(requestDto);
    }
    // 팔로우 해제
    @DeleteMapping("/api/v1/follows/{userId}/{followTargetId}")
    public void deleteFollow(@PathVariable Long userId, @PathVariable Long followTargetId) {
        followsService.deleteFollow(userId, followTargetId);
    }
    // 유저 팔로워 조회 첫목록
    @GetMapping("/api/v1/user/{userId}/follower")
    public List<UserResponseDto> getFollowerList(@PathVariable Long userId) {
        return followsService.findFollowerListByUserId(userId, null);
    }
    //유저 팔로워 조회 다음목록
    @GetMapping("/api/v1/user/{userId}/follower/{lastFollowsId}")
    public List<UserResponseDto> getFollowerList(@PathVariable Long userId, @PathVariable Long lastFollowsId) {
        return followsService.findFollowerListByUserId(userId, lastFollowsId);
    }
    // 유저 팔로잉 조회 첫목록
    @GetMapping("/api/v1/user/{userId}/following")
    public List<UserResponseDto> getFollowingList(@PathVariable Long userId) {
        return followsService.findFollowingListByUserId(userId, null);
    }
    // 유저 팔로잉 조회 다음록록
    @GetMapping("/api/v1/user/{userId}/following/{lastFollowsId}")
    public List<UserResponseDto> getFollowingList(@PathVariable Long userId, @PathVariable Long lastFollowsId) {
        return followsService.findFollowingListByUserId(userId, lastFollowsId);
    }
    // 키워드로 유저 검색
    @GetMapping("/api/v1/user/search/{keyword}/{pageNumber}")
    public List<UserResponseDto> searchUser(@PathVariable String keyword, @PathVariable Integer pageNumber) {
        return userService.searchUser(keyword, pageNumber);
    }
    // 유저가 작성한 게시물 조회 첫목록
    @GetMapping("/api/v1/user/{userId}/posts")
    public List<PostsAllResponseDto> getUserPosts(@PathVariable Long userId) {
        return postsService.findByUserId(userId, null);
    }
    // 유저가 작성한 게시물 조회 다음목록
    @GetMapping("/api/v1/user/{userId}/posts/{lastPostId}")
    public List<PostsAllResponseDto> getUserPosts(@PathVariable Long userId, @PathVariable Long lastPostId) {
        return postsService.findByUserId(userId, lastPostId);
    }
    // 유저가 누른 좋아요 목록
    @GetMapping("/api/v1/user/{userId}/like")
    public LikeListResponseDto getUserLike(@PathVariable Long userId) {
        return userService.findLikeById(userId);
    }

    // 유저 랜덤 추천
    @GetMapping("/api/v1/recommend/user")
    public List<UserResponseDto> randomUser() {
        return userService.randomUser();
    }
    // 태그 랜덤 추천
    @GetMapping("/api/v1/recommend/tag")
    public List<TagResponseDto> randomTag() {
        return tagsService.randomTag();
    }



}
