package com.maen.vlogwebserviceserver.service.posts;


import com.maen.vlogwebserviceserver.web.dto.PostsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.jcodec.api.FrameGrab;
import org.jcodec.api.JCodecException;
import org.jcodec.common.io.IOUtils;
import org.jcodec.common.model.Picture;
import org.jcodec.scale.AWTUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

@RequiredArgsConstructor
@Service
public class MediaService {

    @Value("${spring.servlet.multipart.location}")
    private String filePath;
    public final String THUMBNAIL_FORMAT = "jpg";

    //영상 파일 저장
    public void save(PostsSaveRequestDto postsSaveRequestDto) throws IOException, JCodecException {
        //비디오 및 썸네일 폴더 생성
        String thumbnailPath = filePath+"thumbnail\\";
        File thumbnailDirectory = new File(thumbnailPath);
        thumbnailDirectory.mkdirs();

        //파일 저장 및 썸네일 생성
        String videoName = UUID.randomUUID()+"_"+postsSaveRequestDto.getVideo().getOriginalFilename();
        postsSaveRequestDto.getVideo().transferTo(new File(filePath+videoName));
        String thumbnailName = makeThumbnail(videoName,thumbnailPath);
        postsSaveRequestDto.setVideoName(videoName);
        postsSaveRequestDto.setThumbnailName(thumbnailName);
    }

    //영상파일 스트리밍 재생
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

    //썸네일 생성
    public String makeThumbnail(String videoName, String thumbnailPath) throws IOException, JCodecException {
        File source = new File(filePath+videoName);
        StringTokenizer st = new StringTokenizer(videoName,".");
        String thumbnailName = st.nextToken()+"."+THUMBNAIL_FORMAT;

        // 0프레임 기준 썸네일 생성
        int frameNumber = 0;
        Picture picture = FrameGrab.getFrameFromFile(source, frameNumber);
        BufferedImage bufferedImage = AWTUtil.toBufferedImage(picture);
        ImageIO.write(bufferedImage, THUMBNAIL_FORMAT, new File(thumbnailPath+thumbnailName));

        return thumbnailName;
    }

    //썸네일 출력
    public ResponseEntity<byte[]> findThumbnailByName(String thumbnailName) throws IOException {
        InputStream inputStream = new FileInputStream(filePath+"thumbnail\\"+thumbnailName);
        byte[] imageBytes = IOUtils.toByteArray(inputStream);
        inputStream.close();
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.IMAGE_JPEG)
                .body(imageBytes);
    }
}
