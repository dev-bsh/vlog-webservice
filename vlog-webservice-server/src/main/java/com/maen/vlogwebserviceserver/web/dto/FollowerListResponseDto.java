package com.maen.vlogwebserviceserver.web.dto;

import com.maen.vlogwebserviceserver.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class FollowerListResponseDto {
    private List<User> followerList;

    public FollowerListResponseDto(List<User> followerList) {
        this.followerList = followerList;
    }
}
