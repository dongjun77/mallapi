package com.project.mallapi.util;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import jakarta.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@Log4j2
@RequiredArgsConstructor
public class CustomFileUtil {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    // S3 업로드 (원본 + 썸네일)
    public List<String> saveFiles(List<MultipartFile> files) throws RuntimeException {

        if (files == null || files.isEmpty()) {
            log.warn("🚨 saveFiles: 파일이 없음!");
            return new ArrayList<>();
        }

        List<String> uploadNames = new ArrayList<>();

        for (MultipartFile file : files) {
            String folder = "product";

            try {
                // [1] 원본 업로드
                String savedName = UUID.randomUUID() + "_" + file.getOriginalFilename();
                String key = folder + "/" + savedName; // S3의 Key는 '폴더명/파일명'으로 작성

                File tempFile = convert(file);
                amazonS3.putObject(new PutObjectRequest(bucket, key, tempFile)
                        .withCannedAcl(CannedAccessControlList.PublicRead));

                String contentType = file.getContentType();

                if(contentType != null && contentType.startsWith("image")) {

                    String thumbnailName = "s_" + savedName; // thumbnail 파일명
                    String thumbnailKey = folder + "/" + thumbnailName; // S3의 Key는 '폴더명/파일명'으로 작성

                    File thumbnailFile = new File(System.getProperty("java.io.tmpdir") + "/" + thumbnailName);
                    Thumbnails.of(tempFile).size(200, 200).toFile(thumbnailFile);

                    amazonS3.putObject(new PutObjectRequest(bucket, thumbnailKey, thumbnailFile)
                            .withCannedAcl(CannedAccessControlList.PublicRead));
                    thumbnailFile.delete();

                }

                tempFile.delete();

                uploadNames.add(savedName);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return uploadNames;
    }

    // S3 삭제
    public void deleteFiles(List<String> fileNames) {

        if (fileNames == null || fileNames.isEmpty()) {
            return;
        }

        fileNames.forEach(fileName -> {
            try {
                amazonS3.deleteObject(new DeleteObjectRequest(bucket, fileName));
                amazonS3.deleteObject(new DeleteObjectRequest(bucket, "s_" + fileName));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    // MultipartFile → File 변환
    private File convert(MultipartFile file) throws IOException {
        File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convFile)) {
            fos.write(file.getBytes());
        }
        return convFile;
    }
}
