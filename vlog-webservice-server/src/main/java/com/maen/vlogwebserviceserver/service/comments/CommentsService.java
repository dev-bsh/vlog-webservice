package com.maen.vlogwebserviceserver.service.comments;

import com.maen.vlogwebserviceserver.domain.comments.Comments;
import com.maen.vlogwebserviceserver.domain.comments.CommentsLikeRepository;
import com.maen.vlogwebserviceserver.domain.comments.CommentsRepository;
import com.maen.vlogwebserviceserver.domain.user.UserRepository;
import com.maen.vlogwebserviceserver.web.dto.CommentsResponseDto;
import com.maen.vlogwebserviceserver.web.dto.CommentsSaveRequestDto;
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
    public List<CommentsResponseDto> findAllByPostsId(Long postsId) {
        List<Comments> commentsList = commentsRepository.findAllByPostsId(postsId);
        List<CommentsResponseDto> responseDtoList = new ArrayList<>();
        for(Comments comments : commentsList) {
            String author = userRepository.getById(comments.getUserId()).getName();
            int commentLike = commentsLikeRepository.countByCommentsId(comments.getId());
            responseDtoList.add(CommentsResponseDto.builder()
                    .author(author)
                    .commentsLike(commentLike)
                    .contents(comments.getContent())
                    .build());
        }
        return responseDtoList;
    }

    @Transactional
    public Long update(Long id, String content) {
        Comments comments = commentsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다. id=" + id));
        comments.update(content);
        return id;
    }

    @Transactional
    public void delete(Long id) {
        Comments comments = commentsRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("해당 댓글이 없습니다. id="+id));
        commentsRepository.delete(comments);
    }
}