package com.maen.vlogwebserviceserver.web;

import com.maen.vlogwebserviceserver.service.user.FollowsService;
import com.maen.vlogwebserviceserver.web.dto.FollowsCountResponseDto;
import com.maen.vlogwebserviceserver.web.dto.FollowsResponseDto;
import com.maen.vlogwebserviceserver.web.dto.FollowsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final FollowsService followsService;

    @PostMapping("/api/v1/follows")
    public Long saveFollow(@RequestBody FollowsSaveRequestDto requestDto) {
        return followsService.saveFollow(requestDto);
    }

    @DeleteMapping("/api/v1/{userId}/{followTargetId}")
    public void deleteFollow(@PathVariable Long userId, @PathVariable Long followTargetId) {
        followsService.deleteFollow(userId, followTargetId);
    }

    @GetMapping("/api/v1/user/{userId}/follows")
    public FollowsCountResponseDto getFollowsCount(@PathVariable Long userId) {
        return followsService.findFollowsCountByUserId(userId);
    }

    @GetMapping("/api/v1/user/{userId}/follower")
    public List<FollowsResponseDto> getFollowerList(@PathVariable Long userId) {
        return followsService.findFollowerListByUserId(userId);
    }

    @GetMapping("/api/v1/user/{userId}/following")
    private List<FollowsResponseDto> getFollowingList(@PathVariable Long userId) {
        return followsService.findFollowingListByUserId(userId);
    }
}
