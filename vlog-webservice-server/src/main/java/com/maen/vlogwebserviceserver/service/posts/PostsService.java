package com.maen.vlogwebserviceserver.service.posts;

import com.maen.vlogwebserviceserver.domain.comments.CommentsRepository;
import com.maen.vlogwebserviceserver.domain.posts.Posts;
import com.maen.vlogwebserviceserver.domain.posts.PostsLikeRepository;
import com.maen.vlogwebserviceserver.domain.posts.PostsRepository;
import com.maen.vlogwebserviceserver.domain.user.User;
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

    @Transactional
    public PostsDetailResponseDto findById(Long id) {
        // 1.posts 불러오기  2.유저이름 불러오기 3.태그 불러오기 4.좋아요 불러오기 5. 댓글 수 불러오기 5.dto 반환
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        User user = userRepository.getById(posts.getUserId());
        List<String> tags = tagsService.findByPostsId(posts.getId());
        int postLike = postsLikeRepository.countByPostsId(posts.getId());
        int totalCommentsCount = commentsRepository.countByPostsId(posts.getId());
        // 조회수 증가
        posts.upViews();

        return PostsDetailResponseDto.builder()
                .posts(posts)
                .user(user)
                .tags(tags)
                .postsLike(postLike)
                .totalCommentsCount(totalCommentsCount)
                .build();
    }

    @Transactional(readOnly = true)
    public List<PostsAllResponseDto> findRecentList(String tag, Long lastPostsId) {
        List<Posts> postsList;
        if(tag == null) {
            postsList = postsRepository.findRecentListInMainPage(lastPostsId);
        }
        else {
            postsList = postsRepository.findRecentListByTagSearch(tag, lastPostsId);
        }

        return getPostsAllResponseDto(postsList);
    }

    @Transactional(readOnly = true)
    public List<PostsAllResponseDto> findPopularList(String tag, Integer pageNumber) {
        List<Posts> postsList;
        if(tag == null) {
            postsList = postsRepository.findPopularListInMainPage(pageNumber);
        }
        else {
            postsList = postsRepository.findPopularListByTagSearch(tag, pageNumber);
        }
        return getPostsAllResponseDto(postsList);

    }

    @Transactional(readOnly = true)
    public List<PostsAllResponseDto> findByUserId(Long userId, Long lastPostId) {
        List<Posts> postsList = postsRepository.findListByUserId(userId, lastPostId);
        return getPostsAllResponseDto(postsList);
    }

    public List<PostsAllResponseDto> getPostsAllResponseDto(List<Posts> postsList) {
        List<PostsAllResponseDto> responseDtoList = new ArrayList<>();
        for(Posts posts : postsList) {
            User user = userRepository.findById(posts.getUserId()).orElseThrow(()-> new IllegalArgumentException("해당 게시물 작성자가 없습니다. id ="+posts.getUserId()));
            int postsLike = postsLikeRepository.countByPostsId(posts.getId());
            List<String> tags = tagsService.findByPostsId(posts.getId());
            responseDtoList.add(PostsAllResponseDto.builder()
                    .postsId(posts.getId())
                    .authorId(user.getId())
                    .authorName(user.getName())
                    .authorPicture(user.getPicture())
                    .postsLike(postsLike)
                    .views(posts.getViews())
                    .thumbnailName(posts.getThumbnailName())
                    .tags(tags)
                    .build());
        }
        return responseDtoList;
    }
}
