package com.maen.vlogwebserviceserver.web.dto;

import com.maen.vlogwebserviceserver.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class FollowingListResponseDto {
    private List<User> followingList;

    public FollowingListResponseDto(List<User> followingList) {
        this.followingList = followingList;
    }
}
