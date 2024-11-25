package com.daniel.s3.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.exception.SdkException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final S3Client s3Client;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    public String uploadImage(MultipartFile file) {
        try {
            String key = "images/" + UUID.randomUUID() + "-" + file.getOriginalFilename();
            s3Client.putObject(builder -> builder.bucket(bucketName).key(key).build(),
                    RequestBody.fromBytes(file.getBytes()));
            return key;
        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler o arquivo: " + e.getMessage(), e);
        } catch (SdkException e) {
            throw new RuntimeException("Erro ao interagir com o S3: " + e.getMessage(), e);
        }
    }

    public void deleteImage(String imageKey) {
        try {
            s3Client.deleteObject(DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(imageKey)
                    .build());
        } catch (SdkException e) {
            throw new RuntimeException("Erro ao deletar a imagem do S3: " + e.getMessage(), e);
        }
    }
}
