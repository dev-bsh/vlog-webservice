package com.maen.vlogwebserviceserver.service.user;


import com.maen.vlogwebserviceserver.domain.user.FollowsRepository;
import com.maen.vlogwebserviceserver.domain.user.User;
import com.maen.vlogwebserviceserver.domain.user.UserRepository;
import com.maen.vlogwebserviceserver.web.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final FollowsRepository followsRepository;

    @Transactional(readOnly = true)
    public UserResponseDto findById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(()-> new IllegalArgumentException("해당 유저가 없습니다. id=" + userId));
        int followerNumber = followsRepository.countByFollowTargetId(userId);
        int followingNumber = followsRepository.countByUserId(userId);
        return UserResponseDto.builder()
                .userId(userId)
                .name(user.getName())
                .email(user.getEmail())
                .picture(user.getPicture())
                .followerNumber(followerNumber)
                .followingNumber(followingNumber)
                .build();
    }


}
