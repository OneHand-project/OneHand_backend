package com.example.demo.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
public class FileUploadService {

    @Autowired
    private S3Client s3Client;

    private final String BUCKET_NAME = "imagehandler";

    public String uploadFile(MultipartFile file, String campaignId)
        throws IOException {
        String key =
            "campaign-images/" + campaignId + "/" + file.getOriginalFilename();

        s3Client.putObject(
            PutObjectRequest.builder()
                .bucket(BUCKET_NAME)
                .key(key)
                .acl("public-read")
                .contentType(file.getContentType())
                .build(),
            RequestBody.fromBytes(file.getBytes())
        );

        return (
            "https://" + BUCKET_NAME + ".fra1.digitaloceanspaces.com/" + key
        );
    }

    public List<String> uploadMultipleFiles(List<MultipartFile> file, String campaignId)
        throws IOException {

        List<String> keys = new ArrayList<>();
        int count =1;
        for (MultipartFile img : file) {
            String key =
                "campaign-images/" +
                campaignId +
                "/description/" +
                count  + "_"+ img.getOriginalFilename();

            s3Client.putObject(
                PutObjectRequest.builder()
                    .bucket(BUCKET_NAME)
                    .key(key)
                    .acl("public-read")
                    .contentType(img.getContentType())
                    .build(),
                RequestBody.fromBytes(img.getBytes())
            );

            keys.add("https://" + BUCKET_NAME + ".fra1.digitaloceanspaces.com/" + key);
            count++;
        }
        return keys;
    }
}
