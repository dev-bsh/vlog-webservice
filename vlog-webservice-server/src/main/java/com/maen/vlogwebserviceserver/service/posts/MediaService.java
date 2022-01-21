package com.maen.vlogwebserviceserver.service.posts;


import com.maen.vlogwebserviceserver.web.dto.PostsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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

    @Value("${ffmpeg.file.path}")
    private String FFMPEG_FILE_PATH;

    @Value("${ffprobe.file.path}")
    private String FFPROBE_FILE_PATH;

    //영상 파일 저장
    public void save(PostsSaveRequestDto postsSaveRequestDto) throws IOException {
        //비디오 및 썸네일 폴더 생성
        File videoDirectory = new File(VIDEO_FILE_PATH);
        File thumbnailDirectory = new File(THUMBNAIL_FILE_PATH);
        videoDirectory.mkdirs();
        thumbnailDirectory.mkdirs();

        //파일 저장
        String videoName = UUID.randomUUID()+"_"+postsSaveRequestDto.getVideo().getOriginalFilename();
        postsSaveRequestDto.getVideo().transferTo(new File(VIDEO_FILE_PATH+videoName));

        //MP4 파일이 아닌경우 변환
        StringTokenizer st = new StringTokenizer(videoName,".");
        String videoNameExceptFormat = st.nextToken();
        String videoFormat = st.nextToken();
        if(!videoFormat.equals("mp4")) {
            String convertedVideoName = fileToMp4(videoNameExceptFormat, videoFormat);
            //변환 후 기존파일 삭제
            File originalVideo = new File(VIDEO_FILE_PATH+videoName);
            originalVideo.delete();
            //변환 된 포맷을 포함한 이름으로 변경
            videoName = convertedVideoName;
        }

        //썸네일 생성
        String thumbnailName = makeThumbnailByFFmpeg(videoName, videoNameExceptFormat);
        postsSaveRequestDto.setVideoName(videoName);
        postsSaveRequestDto.setThumbnailName(thumbnailName);
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
    public String makeThumbnailByFFmpeg(String videoName, String videoNameExceptFormat) throws IOException {

        FFmpeg ffmpeg = new FFmpeg(FFMPEG_FILE_PATH);
        FFprobe ffprobe = new FFprobe(FFPROBE_FILE_PATH);
        String thumbnailName = videoNameExceptFormat+"."+THUMBNAIL_FORMAT;

        FFmpegBuilder builder = new FFmpegBuilder()
                .overrideOutputFiles(true)
                .setInput(VIDEO_FILE_PATH+videoName)
                .addExtraArgs("-ss","00:00:00")
                .addOutput(THUMBNAIL_FILE_PATH+thumbnailName)
                    .setFrames(1)
                    .done();

        FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
        executor.createJob(builder).run();

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

    //영상 파일 MP4로 변환
    public String fileToMp4(String videoNameExceptFormat, String originalVideoFormat) throws IOException {
        FFmpeg ffmpeg = new FFmpeg(FFMPEG_FILE_PATH);
        FFprobe ffprobe = new FFprobe(FFPROBE_FILE_PATH);
        String originalVideoName = videoNameExceptFormat+"."+originalVideoFormat;
        String convertedVideoName = videoNameExceptFormat+"."+VIDEO_FILE_FORMAT;

        FFmpegBuilder builder = new FFmpegBuilder()
                .setInput(VIDEO_FILE_PATH+originalVideoName)
                .overrideOutputFiles(true)
                .addOutput(VIDEO_FILE_PATH+convertedVideoName)
                    .setFormat(VIDEO_FILE_FORMAT)
                    .setVideoCodec("libx264")
                    .disableSubtitle()
                    .setAudioChannels(2)
                    .setVideoBitRate(1464800)
                    .setStrict(FFmpegBuilder.Strict.EXPERIMENTAL)
                    .done();

        FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
        executor.createJob(builder).run();

        return convertedVideoName;
    }
}
