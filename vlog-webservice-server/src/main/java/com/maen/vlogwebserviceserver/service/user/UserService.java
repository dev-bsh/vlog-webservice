package com.maen.vlogwebserviceserver.service.user;


import com.maen.vlogwebserviceserver.domain.user.FollowsRepository;
import com.maen.vlogwebserviceserver.domain.user.User;
import com.maen.vlogwebserviceserver.domain.user.UserRepository;
import com.maen.vlogwebserviceserver.service.comments.CommentsLikeService;
import com.maen.vlogwebserviceserver.service.posts.PostsLikeService;
import com.maen.vlogwebserviceserver.web.dto.LikeListResponseDto;
import com.maen.vlogwebserviceserver.web.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final FollowsRepository followsRepository;
    private final CommentsLikeService commentsLikeService;
    private final PostsLikeService postsLikeService;

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

    @Transactional(readOnly = true)
    public List<UserResponseDto> searchUser(String keyword, Integer pageNumber) {
        List<User> searchResult = userRepository.findUserByKeyword(keyword, pageNumber);
        return getUserResponseDtoList(searchResult);
    }

    @Transactional(readOnly = true)
    public LikeListResponseDto findLikeById(Long userId) {
        List<Long> postIds = postsLikeService.findAllPostsIdByUserId(userId);
        List<Long> commentIds = commentsLikeService.findAllCommentsIdByUserId(userId);
        return LikeListResponseDto.builder()
                .userLikePostIds(postIds)
                .userLikeCommentIds(commentIds)
                .build();
    }

    @Transactional(readOnly = true)
    public List<UserResponseDto> randomUser() {
        int totalCount = (int) userRepository.count();
        int random = (int) (Math.random() * (totalCount-5));
        Page<User> userPage = userRepository.findAll(PageRequest.of(random,5));
        List<User> userList = userPage.getContent();
        return getUserResponseDtoList(userList);
    }

    public List<UserResponseDto> getUserResponseDtoList(List<User> userList) {
        List<UserResponseDto> responseDtoList = new ArrayList<>();
        for(User user : userList) {
            responseDtoList.add(UserResponseDto.builder()
                    .userId(user.getId())
                    .name(user.getName())
                    .email(user.getEmail())
                    .picture(user.getPicture())
                    .followerNumber(followsRepository.countByFollowTargetId(user.getId()))
                    .followingNumber(followsRepository.countByUserId(user.getId()))
                    .build()
            );
        }
        return responseDtoList;
    }
}
