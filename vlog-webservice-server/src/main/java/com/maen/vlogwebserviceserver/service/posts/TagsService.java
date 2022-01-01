package com.maen.vlogwebserviceserver.service.posts;


import com.maen.vlogwebserviceserver.domain.posts.Tags;
import com.maen.vlogwebserviceserver.domain.posts.TagsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

@RequiredArgsConstructor
@Service
public class TagsService {

    private final TagsRepository tagsRepository;

    @Transactional
    public List<Long> save(String tags) {
        StringTokenizer st =new StringTokenizer(tags,"#");
        List<Long> tagIds = new ArrayList<>();
        while (st.hasMoreTokens()) {
            tagIds.add(tagsRepository.save(Tags.builder()
                    .content(st.nextToken())
                    .build()).getId());
        }
        return tagIds;
    }


}
