package com.maen.vlogwebserviceserver.service.posts;


import com.maen.vlogwebserviceserver.web.dto.PostsSaveRequestDto;
import com.maen.vlogwebserviceserver.web.dto.PostsUpdateRequestDto;
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
import java.util.Optional;
import java.util.StringTokenizer;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class MediaService {

    @Value("${video.file.path}")
    private String VIDEO_FILE_PATH;

    @Value("${video.file.format}")
    private String VIDEO_FILE_FORMAT;

    @Value("${thumbnail.file.path}")
    private String THUMBNAIL_FILE_PATH;

    @Value("${thumbnail.file.format}")
    private String THUMBNAIL_FORMAT;


    //영상 파일 저장
    public void save(PostsSaveRequestDto postsSaveRequestDto) throws IOException, JCodecException {
        //비디오 및 썸네일 폴더 생성
        File videoDirectory = new File(VIDEO_FILE_PATH);
        File thumbnailDirectory = new File(THUMBNAIL_FILE_PATH);
        videoDirectory.mkdirs();
        thumbnailDirectory.mkdirs();

        //파일 저장
        String videoName = UUID.randomUUID()+"_"+postsSaveRequestDto.getVideo().getOriginalFilename();
        postsSaveRequestDto.getVideo().transferTo(new File(VIDEO_FILE_PATH+videoName));

        //썸네일 생성
        String thumbnailName = makeThumbnail(videoName);
        postsSaveRequestDto.setVideoName(videoName);
        postsSaveRequestDto.setThumbnailName(thumbnailName);
    }

    public void delete(String videoName, String thumbnailName) {
        File video = new File(VIDEO_FILE_PATH+videoName);
        File thumbnail = new File(THUMBNAIL_FILE_PATH+thumbnailName);
        video.delete();
        thumbnail.delete();
    }

    public void update(PostsUpdateRequestDto updateRequestDto, String videoName, String thumbnailName) throws IOException, JCodecException {
        //새파일 저장
        String newVideoName = UUID.randomUUID()+"_"+updateRequestDto.getVideo().getOriginalFilename();
        updateRequestDto.getVideo().transferTo(new File(VIDEO_FILE_PATH+videoName));

        //새썸네일 생성
        String newThumbnailName = makeThumbnail(newVideoName);
        updateRequestDto.setVideoName(newVideoName);
        updateRequestDto.setThumbnailName(newThumbnailName);

        //기존 파일 삭제
        delete(videoName, thumbnailName);
    }


    //영상파일 스트리밍 재생
    public ResponseEntity<ResourceRegion> findVideoByName(HttpHeaders httpHeaders, String videoName) throws Exception {
        UrlResource video = new UrlResource("file:"+VIDEO_FILE_PATH+videoName);
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
                .contentType(MediaTypeFactory.getMediaType(videoName).orElse(MediaType.APPLICATION_OCTET_STREAM))
                .body(resourceRegion);

    }

    //썸네일 생성
    public String makeThumbnail(String videoName) throws IOException, JCodecException {
        File source = new File(VIDEO_FILE_PATH+videoName);
        StringTokenizer st = new StringTokenizer(videoName,".");
        String thumbnailName = st.nextToken()+"."+THUMBNAIL_FORMAT;

        // 0프레임 기준 썸네일 생성
        int frameNumber = 0;
        Picture picture = FrameGrab.getFrameFromFile(source, frameNumber);
        BufferedImage bufferedImage = AWTUtil.toBufferedImage(picture);
        ImageIO.write(bufferedImage, THUMBNAIL_FORMAT, new File(THUMBNAIL_FILE_PATH+thumbnailName));

        return thumbnailName;
    }

    //썸네일 출력
    public ResponseEntity<byte[]> findThumbnailByName(String thumbnailName) throws IOException {
        InputStream inputStream = new FileInputStream(THUMBNAIL_FILE_PATH+thumbnailName);
        byte[] imageBytes = IOUtils.toByteArray(inputStream);
        inputStream.close();
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.IMAGE_JPEG)
                .body(imageBytes);
    }

}
