package com.maen.vlogwebserviceserver.service.posts;

import com.maen.vlogwebserviceserver.domain.comments.CommentsRepository;
import com.maen.vlogwebserviceserver.domain.posts.Posts;
import com.maen.vlogwebserviceserver.domain.posts.PostsLikeRepository;
import com.maen.vlogwebserviceserver.domain.posts.PostsRepository;
import com.maen.vlogwebserviceserver.domain.user.UserRepository;
import com.maen.vlogwebserviceserver.web.dto.PostsResponseDto;
import com.maen.vlogwebserviceserver.web.dto.PostsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.jcodec.api.JCodecException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;
    private final UserRepository userRepository;
    private final PostsLikeRepository postsLikeRepository;
    private final CommentsRepository commentsRepository;
    private final TagsService tagsService;
    private final  VideoService videoService;

    @Transactional
    public Long save(PostsSaveRequestDto postsSaveRequestDto) throws IOException, JCodecException {
        String videoName = videoService.save(postsSaveRequestDto);
        Long postsId = postsRepository.save(postsSaveRequestDto.toEntity(videoName)).getId();
        tagsService.save(postsSaveRequestDto.getTags(),postsId);
        return postsId;
    }

    @Transactional(readOnly = true)
    public PostsResponseDto findById(Long id) {
        // 1.posts 불러오기  2.유저이름 불러오기 3.태그 불러오기 4.좋아요 불러오기 5. 댓글 수 불러오기 5.dto 반환
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        String author = userRepository.getById(posts.getUserId()).getName();
        String tags = tagsService.findByPostsId(posts.getId());
        int postLike = postsLikeRepository.countByPostsId(posts.getId());
        int commentsCount = commentsRepository.countByPostsId(posts.getId());
        // 조회수 증가
        posts.upViews();

        return PostsResponseDto.builder()
                .posts(posts)
                .author(author)
                .tags(tags)
                .postsLike(postLike)
                .commentsCount(commentsCount)
                .build();
    }



}
