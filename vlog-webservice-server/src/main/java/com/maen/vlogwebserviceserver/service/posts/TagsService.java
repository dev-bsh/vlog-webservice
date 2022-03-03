package com.maen.vlogwebserviceserver.service.posts;

import com.maen.vlogwebserviceserver.domain.posts.PostsTags;
import com.maen.vlogwebserviceserver.domain.posts.PostsTagsRepository;
import com.maen.vlogwebserviceserver.domain.posts.Tags;
import com.maen.vlogwebserviceserver.domain.posts.TagsRepository;
import com.maen.vlogwebserviceserver.web.dto.TagResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

@RequiredArgsConstructor
@Service
public class TagsService {

    private final TagsRepository tagsRepository;
    private final PostsTagsRepository postsTagsRepository;

    public void save(String postInput, Long postsId) {
        StringTokenizer st = new StringTokenizer(postInput,"#");
        while(st.hasMoreTokens()){
            String content = st.nextToken();
            Tags tags;
            if(!tagsRepository.existsByContent(content)){
                tags = tagsRepository.save(Tags.builder()
                        .content(content)
                        .build());
            }
            else {
                tags = tagsRepository.findByContent(content);
                tags.countUp();
            }
            postsTagsRepository.save(PostsTags.builder()
                    .postsId(postsId)
                    .tagsId(tags.getId())
                    .build());
        }
    }

    public List<String> findByPostsId(Long postsId) {
        List<PostsTags> postsTags = postsTagsRepository.findByPostsId(postsId);
        List<Long> tagsIds = new ArrayList<>();
        for(PostsTags postTag : postsTags) {
            tagsIds.add(postTag.getTagsId());
        }

        List<Tags> tagsList = tagsRepository.findAllById(tagsIds);
        List<String> tags = new ArrayList<>();

        for(Tags tag : tagsList) {
            tags.add(tag.getContent());
        }
        return tags;
    }

    @Transactional
    public void deleteByPostsId(Long postsId) {
        List<PostsTags> tagsList = postsTagsRepository.findByPostsId(postsId);
        for(PostsTags postsTags : tagsList) {
            Tags tags = tagsRepository.findById(postsTags.getTagsId()).orElseThrow(() -> new IllegalArgumentException("해당 태그가 없습니다. id ="+postsTags.getTagsId()));
            tags.countDown();
            if(tags.getCount() == 0) {
                tagsRepository.delete(tags);
            }
        }
        postsTagsRepository.findByPostsId(postsId);
    }

    @Transactional
    public void update(Long postsId, String updateTags) {
        List<PostsTags> postsTagsList = postsTagsRepository.findByPostsId(postsId);
        List<Tags> originTagList = new ArrayList<>();
        for(PostsTags postsTags : postsTagsList) {
            Tags tags = tagsRepository.findById(postsTags.getTagsId()).orElseThrow(() -> new IllegalArgumentException("해당 태그가 없습니다. id ="+postsTags.getTagsId()));
            originTagList.add(tags);
        }
        StringTokenizer st = new StringTokenizer(updateTags,"#");
        List<String> newTagList = new ArrayList<>();
        while(st.hasMoreTokens()) {
            newTagList.add(st.nextToken());
        }
        // 추가된 태그 저장
        for(String newTag : newTagList) {
            boolean isExists = false;
            for(Tags originTag : originTagList) {
                if(newTag.equals(originTag.getContent())){
                    isExists = true;
                    break;
                }
            }
            if(!isExists) {
                save(newTag, postsId);
            }
        }
        // 제거된 태그 삭제 (카운트 감소시키고 카운트 0 이면 아에 제거)
        for(Tags originTag : originTagList) {
            boolean isExists = false;
            for(String newTag : newTagList) {
                if(originTag.getContent().equals(newTag)) {
                    isExists = true;
                    break;
                }
            }
            if(!isExists) {
                postsTagsRepository.deleteByTagsIdAndPostsId(originTag.getId(), postsId);
                originTag.countDown();
                if(originTag.getCount() == 0) {
                    tagsRepository.delete(originTag);
                }
            }
        }
    }

    @Transactional(readOnly = true)
    public List<TagResponseDto> randomTag() {
        int totalCount = (int) tagsRepository.count();
        if(totalCount > 5) {
            totalCount -= 5;
        }
        int random = (int) (Math.random() * totalCount);
        Page<Tags> tagsPage = tagsRepository.findAll(PageRequest.of(random,5));
        List<Tags> tagList = tagsPage.getContent();
        List<TagResponseDto> tagResponseDtoList = new ArrayList<>();
        for(Tags tags : tagList) {
            tagResponseDtoList.add(TagResponseDto.builder()
                    .tagId(tags.getId())
                    .content(tags.getContent())
                    .build()
            );
        }
        return tagResponseDtoList;
    }
}
