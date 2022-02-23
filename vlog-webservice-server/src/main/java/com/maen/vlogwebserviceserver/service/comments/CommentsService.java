package com.maen.vlogwebserviceserver.service.comments;

import com.maen.vlogwebserviceserver.domain.comments.Comments;
import com.maen.vlogwebserviceserver.domain.comments.CommentsLikeRepository;
import com.maen.vlogwebserviceserver.domain.comments.CommentsRepository;
import com.maen.vlogwebserviceserver.domain.user.User;
import com.maen.vlogwebserviceserver.domain.user.UserRepository;
import com.maen.vlogwebserviceserver.web.dto.CommentsAllResponseDto;
import com.maen.vlogwebserviceserver.web.dto.CommentsSaveRequestDto;
import com.maen.vlogwebserviceserver.web.dto.CommentsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentsService {

    private final CommentsRepository commentsRepository;
    private final UserRepository userRepository;
    private final CommentsLikeRepository commentsLikeRepository;

    @Transactional
    public Long save(CommentsSaveRequestDto saveRequestDto) {
        return commentsRepository.save(saveRequestDto.toEntity()).getId();
    }

    @Transactional
    public List<CommentsAllResponseDto> findAllInPostsDetail(Long postsId, Long commentsId) {
        List<Comments> commentsList = commentsRepository.findAllInPostsDetail(postsId, commentsId);
        List<CommentsAllResponseDto> responseDtoList = new ArrayList<>();
        for(Comments comments : commentsList) {
            User author = userRepository.findById(comments.getUserId()).orElseThrow(()-> new IllegalArgumentException("없는 사용자 입니다. id="+comments.getUserId()));
            int commentLike = commentsLikeRepository.countByCommentsId(comments.getId());
            responseDtoList.add(CommentsAllResponseDto.builder()
                    .commentId(comments.getId())
                    .authorId(author.getId())
                    .author(author.getName())
                    .authorPicture(author.getPicture())
                    .commentsLike(commentLike)
                    .contents(comments.getContent())
                    .build());
        }
        return responseDtoList;
    }

    @Transactional
    public Long update(Long id, CommentsUpdateRequestDto updateRequestDto) {
        Comments comments = commentsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다. id=" + id));
        comments.update(updateRequestDto);
        return id;
    }

    @Transactional
    public void delete(Long id) {
        Comments comments = commentsRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("해당 댓글이 없습니다. id="+id));
        commentsRepository.delete(comments);
    }
}
