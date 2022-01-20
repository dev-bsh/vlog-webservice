package com.maen.vlogwebserviceserver.service.comments;

import com.maen.vlogwebserviceserver.domain.comments.CommentsLikeRepository;
import com.maen.vlogwebserviceserver.web.dto.CommentsLikeSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CommentsLikeService {

    private final CommentsLikeRepository commentsLikeRepository;

    @Transactional
    public Long save(CommentsLikeSaveRequestDto commentsLikeSaveRequestDto) {
        return commentsLikeRepository.save(commentsLikeSaveRequestDto.toEntity()).getId();
    }

    @Transactional
    public void delete(Long commentsId, Long userId) {
        commentsLikeRepository.deleteByCommentsIdAndUserId(commentsId,userId);
    }
}
