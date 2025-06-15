package com.example.demo.service;

import java.net.URI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class SpaceConfig {

    @Value("${do.spaces.accessKey}")
    private String accessKey;

    @Value("${do.spaces.secretKey}")
    private String secretKey;

    @Bean
    public S3Client s3Client() {
        AwsBasicCredentials credentials = AwsBasicCredentials.create(
            accessKey,
            secretKey
        );
        return S3Client.builder()
            .region(Region.US_EAST_1) // use Region.US_EAST_1 even if your space is in nyc3
            .endpointOverride(URI.create("https://fra1.digitaloceanspaces.com"))
            .credentialsProvider(StaticCredentialsProvider.create(credentials))
            .build();
    }
}
