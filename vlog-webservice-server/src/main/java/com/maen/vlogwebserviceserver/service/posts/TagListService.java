package com.maen.vlogwebserviceserver.service.posts;


import com.maen.vlogwebserviceserver.domain.posts.TagList;
import com.maen.vlogwebserviceserver.domain.posts.TagListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TagListService {

    private final TagListRepository tagListRepository;

    @Transactional
    public void save(List<Long> tagIds, Long postsId) {
        for (Long tagId : tagIds) {
            tagListRepository.save(TagList.builder()
                    .tagsId(tagId)
                    .postsId(postsId)
                    .build());
        }
    }


}
