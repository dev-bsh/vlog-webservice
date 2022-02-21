package com.maen.vlogwebserviceserver.service.user;

import com.maen.vlogwebserviceserver.domain.user.FollowsRepository;
import com.maen.vlogwebserviceserver.domain.user.User;
import com.maen.vlogwebserviceserver.domain.user.UserRepository;
import com.maen.vlogwebserviceserver.web.dto.FollowsCountResponseDto;
import com.maen.vlogwebserviceserver.web.dto.FollowsResponseDto;
import com.maen.vlogwebserviceserver.web.dto.FollowsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class FollowsService {
    private final FollowsRepository followsRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long saveFollow(FollowsSaveRequestDto requestDto) {
        return followsRepository.save(requestDto.toEntity()).getId();

    }

    @Transactional
    public void deleteFollow(Long userId, Long followTargetId) {
        followsRepository.deleteByUserIdAndFollowTargetId(userId, followTargetId);
    }

    @Transactional(readOnly = true)
    public FollowsCountResponseDto findFollowsCountByUserId(Long userId) {
        int followingCount = followsRepository.countByUserId(userId);
        int followerCount = followsRepository.countByFollowTargetId(userId);
        return FollowsCountResponseDto.builder()
                .following(followingCount)
                .follower(followerCount)
                .build();
    }

    @Transactional(readOnly = true)
    public List<FollowsResponseDto> findFollowerListByUserId(Long userId) {
        List<Long> followerIds = followsRepository.findAllByFollowTargetId(userId);
        List<User> followerList = userRepository.findAllById(followerIds);
        return getAllFollowsResponseDto(followerList);
    }

    @Transactional(readOnly = true)
    public List<FollowsResponseDto> findFollowingListByUserId(Long userId) {
        List<Long> followingIds = followsRepository.findAllByUserId(userId);
        List<User> followingList = userRepository.findAllById(followingIds);
        return getAllFollowsResponseDto(followingList);
    }

    private List<FollowsResponseDto> getAllFollowsResponseDto(List<User> userList) {
        List<FollowsResponseDto> followsResponseDtoList = new ArrayList<>();
        for (User user : userList) {
            followsResponseDtoList.add(FollowsResponseDto.builder()
                    .userId(user.getId())
                    .name(user.getName())
                    .picture(user.getPicture())
                    .email(user.getEmail())
                    .followingNumber(followsRepository.countByUserId(user.getId()))
                    .followerNumber(followsRepository.countByFollowTargetId(user.getId()))
                    .build()
            );
        }
        return followsResponseDtoList;
    }
}
