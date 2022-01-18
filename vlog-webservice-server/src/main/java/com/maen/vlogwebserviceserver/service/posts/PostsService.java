package com.maen.vlogwebserviceserver.service.posts;

import com.maen.vlogwebserviceserver.domain.comments.CommentsRepository;
import com.maen.vlogwebserviceserver.domain.posts.Posts;
import com.maen.vlogwebserviceserver.domain.posts.PostsLikeRepository;
import com.maen.vlogwebserviceserver.domain.posts.PostsRepository;
import com.maen.vlogwebserviceserver.domain.user.UserRepository;
import com.maen.vlogwebserviceserver.web.dto.PostsAllResponseDto;
import com.maen.vlogwebserviceserver.web.dto.PostsDetailResponseDto;
import com.maen.vlogwebserviceserver.web.dto.PostsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.jcodec.api.JCodecException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;
    private final UserRepository userRepository;
    private final PostsLikeRepository postsLikeRepository;
    private final CommentsRepository commentsRepository;
    private final TagsService tagsService;
    private final MediaService mediaService;

    @Transactional
    public Long save(PostsSaveRequestDto postsSaveRequestDto) throws IOException, JCodecException {
        // 1.영상 저장 및 영상&썸네일 파일 이름 지정 2.posts entity 생성 3.tag 저장
        mediaService.save(postsSaveRequestDto);
        Long postsId = postsRepository.save(postsSaveRequestDto.toEntity()).getId();
        tagsService.save(postsSaveRequestDto.getTags(),postsId);
        return postsId;
    }

    @Transactional(readOnly = true)
    public PostsDetailResponseDto findById(Long id) {
        // 1.posts 불러오기  2.유저이름 불러오기 3.태그 불러오기 4.좋아요 불러오기 5. 댓글 수 불러오기 5.dto 반환
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        String author = userRepository.getById(posts.getUserId()).getName();
        String tags = tagsService.findByPostsId(posts.getId());
        int postLike = postsLikeRepository.countByPostsId(posts.getId());
        int commentsCount = commentsRepository.countByPostsId(posts.getId());
        // 조회수 증가
        posts.upViews();

        return PostsDetailResponseDto.builder()
                .posts(posts)
                .author(author)
                .tags(tags)
                .postsLike(postLike)
                .commentsCount(commentsCount)
                .build();
    }

    @Transactional(readOnly = true)
    public List<PostsAllResponseDto> findAll(Long last_post_id) {
        List<Posts> postsList = postsRepository.findNextPosts(last_post_id);
        List<PostsAllResponseDto> responseDtoList = new ArrayList<>();
        for(Posts posts : postsList) {
            String author = userRepository.getById(posts.getUserId()).getName();
            int postsLike = postsLikeRepository.countByPostsId(posts.getId());

            responseDtoList.add(PostsAllResponseDto.builder()
                    .author(author)
                    .postsLike(postsLike)
                    .views(posts.getViews())
                    .thumbnailName(posts.getThumbnailName())
                    .build());
        }
        return responseDtoList;
    }
}
