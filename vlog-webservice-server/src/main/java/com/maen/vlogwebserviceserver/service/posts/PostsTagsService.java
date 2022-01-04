package com.maen.vlogwebserviceserver.service.posts;


import com.maen.vlogwebserviceserver.domain.posts.PostsTags;
import com.maen.vlogwebserviceserver.domain.posts.PostsTagsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PostsTagsService {

    private final PostsTagsRepository tagListRepository;

    @Transactional
    public void save(List<Long> tagIds, Long postsId) {
        for (Long tagId : tagIds) {
            tagListRepository.save(PostsTags.builder()
                    .tagsId(tagId)
                    .postsId(postsId)
                    .build());
        }
    }

    @Transactional
    public List<Long> findTagsIdByPostsId(Long id) {
        // postsID와 매핑되는 tagsId 목록 반환
        List<Long> tagIds = new ArrayList<>();
        for (Long ids:tagListRepository.findByPostsId(id)) {
            tagIds.add(tagListRepository.getById(ids).getTagsId());
        }
        return tagIds;
    }


}
