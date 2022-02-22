package com.maen.vlogwebserviceserver.service.user;

import com.maen.vlogwebserviceserver.domain.user.Follows;
import com.maen.vlogwebserviceserver.domain.user.FollowsRepository;
import com.maen.vlogwebserviceserver.domain.user.User;
import com.maen.vlogwebserviceserver.domain.user.UserRepository;
import com.maen.vlogwebserviceserver.web.dto.FollowsSaveRequestDto;
import com.maen.vlogwebserviceserver.web.dto.UserResponseDto;
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
    public List<UserResponseDto> findFollowerListByUserId(Long userId, Long lastFollowsId) {
        List<Follows> followsList = followsRepository.findFollowerList(userId, lastFollowsId);
        List<Long> followsIds = new ArrayList<>();
        List<Long> followerIds = new ArrayList<>();
        for(Follows follows : followsList) {
            followsIds.add(follows.getId());
            followerIds.add(follows.getUserId());
        }
        List<User> followerList = userRepository.findAllById(followerIds);
        return getAllFollowsResponseDto(followerList, followsIds);
    }

    @Transactional(readOnly = true)
    public List<UserResponseDto> findFollowingListByUserId(Long userId, Long lastFollowsId) {
        List<Follows> followsList = followsRepository.findFollowingList(userId, lastFollowsId);
        List<Long> followsIds = new ArrayList<>();
        List<Long> followingIds = new ArrayList<>();
        for(Follows follows : followsList) {
            followsIds.add(follows.getId());
            followingIds.add(follows.getFollowTargetId());
        }
        List<User> followingList = userRepository.findAllById(followingIds);
        return getAllFollowsResponseDto(followingList, followsIds);
    }

    private List<UserResponseDto> getAllFollowsResponseDto(List<User> userList, List<Long> followsIds) {
        List<UserResponseDto> followsResponseDtoList = new ArrayList<>();
        for (int i = 0; i < userList.size(); i++) {
            User user = userList.get(i);
            Long followsId = followsIds.get(i);
            followsResponseDtoList.add(UserResponseDto.builder()
                    .userId(user.getId())
                    .name(user.getName())
                    .picture(user.getPicture())
                    .email(user.getEmail())
                    .followsId(followsId)
                    .followingNumber(followsRepository.countByUserId(user.getId()))
                    .followerNumber(followsRepository.countByFollowTargetId(user.getId()))
                    .build()
            );
        }
        return followsResponseDtoList;
    }
}
