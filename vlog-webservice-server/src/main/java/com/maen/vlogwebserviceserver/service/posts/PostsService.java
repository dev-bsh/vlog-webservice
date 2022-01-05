package com.maen.vlogwebserviceserver.service.posts;

import com.maen.vlogwebserviceserver.domain.posts.Posts;
import com.maen.vlogwebserviceserver.domain.posts.PostsRepository;
import com.maen.vlogwebserviceserver.domain.user.UserRepository;
import com.maen.vlogwebserviceserver.web.dto.PostsResponseDto;
import com.maen.vlogwebserviceserver.web.dto.PostsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;
    private final UserRepository userRepository;
    private final TagsService tagsService;


    @Value("${spring.servlet.multipart.location}")
    private String filePath;

    @Transactional
    public Long save(PostsSaveRequestDto postsSaveRequestDto) throws IOException {
        String videoName = UUID.randomUUID()+"_"+postsSaveRequestDto.getVideo().getOriginalFilename();
        postsSaveRequestDto.getVideo().transferTo(new File(filePath+videoName));
        Long postsId = postsRepository.save(postsSaveRequestDto.toEntity(videoName)).getId();
        tagsService.save(postsSaveRequestDto.getTags(),postsId);
        return postsId;
    }

    @Transactional(readOnly = true)
    public PostsResponseDto findById(Long id) throws IOException{
        // 1.posts 불러오기 2.파일 불러오기 3.유저이름 불러오기 4.태그 불러오기 5.좋아요 불러오기 6.dto 반환
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        File video = new File(filePath+posts.getVideoName());
        String author = userRepository.getById(posts.getUserId()).getName();
        List<String> tags = tagsService.findByPostsId(posts.getId());

        return PostsResponseDto.builder()
                .tags(tags)
                .author(author)
                .posts(posts)
                .video(video)
                .build();

    }
}
