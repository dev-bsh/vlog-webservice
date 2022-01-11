package com.maen.vlogwebserviceserver.service.posts;


import com.maen.vlogwebserviceserver.web.dto.PostsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class VideoService {

    @Value("${spring.servlet.multipart.location}")
    private String filePath;

    public String save(PostsSaveRequestDto postsSaveRequestDto) throws IOException {
        String videoName = UUID.randomUUID()+"_"+postsSaveRequestDto.getVideo().getOriginalFilename();
        postsSaveRequestDto.getVideo().transferTo(new File(filePath+videoName));

        return videoName;
    }

    public ResponseEntity<ResourceRegion> findVideoByName(HttpHeaders httpHeaders, String videoName) throws Exception {

        UrlResource video = new UrlResource("file:"+filePath+videoName);
        ResourceRegion resourceRegion;
        final long chunkSize = 1000000L;
        long contentLength = video.contentLength();
        Optional<HttpRange> optional = httpHeaders.getRange().stream().findFirst();
        HttpRange httpRange;

        if (optional.isPresent()) {
            httpRange = optional.get();
            long start = httpRange.getRangeStart(contentLength);
            long end = httpRange.getRangeEnd(contentLength);
            long rangeLength = Long.min(chunkSize, end - start + 1);
            resourceRegion = new ResourceRegion(video, start, rangeLength);
        }
        else {
            long rangeLength = Long.min(chunkSize, contentLength);
            resourceRegion = new ResourceRegion(video, 0, rangeLength);
        }

        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                .contentType(MediaTypeFactory.getMediaType(video).orElse(MediaType.APPLICATION_OCTET_STREAM))
                .body(resourceRegion);

    }


}
