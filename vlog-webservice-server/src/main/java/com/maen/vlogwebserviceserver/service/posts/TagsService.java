package com.maen.vlogwebserviceserver.service.posts;

import com.maen.vlogwebserviceserver.domain.posts.PostsTags;
import com.maen.vlogwebserviceserver.domain.posts.PostsTagsRepository;
import com.maen.vlogwebserviceserver.domain.posts.Tags;
import com.maen.vlogwebserviceserver.domain.posts.TagsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    public String findByPostsId(Long postsId) {
        List<PostsTags> postsTags = postsTagsRepository.findByPostsId(postsId);
        List<Long> tagsIds = new ArrayList<>();
        for(PostsTags postTag : postsTags) {
            tagsIds.add(postTag.getTagsId());
        }

        List<Tags> tagsList = tagsRepository.findAllById(tagsIds);
        String tags ="";

        for(Tags tag : tagsList) {
            tags += tag.contentToString();
        }
        return tags;
    }



}
