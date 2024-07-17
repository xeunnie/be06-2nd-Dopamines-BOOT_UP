package com.example.dopamines.global.infra.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class CloudFileUploadService {
    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    @Value("${cloud.aws.region.static}")
    private String location;
    private final AmazonS3 amazonS3;


    public String makeFolder(String rootType) {
        String folder = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));

        String folderPath = rootType + "/" + folder;

        File uploadPathFolder = new File(folderPath);
        if (!uploadPathFolder.exists()) {
            uploadPathFolder.mkdirs();
        }

        return folderPath;
    }


    public String upload(MultipartFile file, String rootType) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());

        String uploadPath = makeFolder(rootType);
        try {
            String fileName = uploadPath + "/" + UUID.randomUUID() + "_" + file.getOriginalFilename();
            amazonS3.putObject(bucketName, fileName, file.getInputStream(), metadata);

            return String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName,location,fileName);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> uploadImages(MultipartFile[] files, String rootType) {

        List<String> paths = new ArrayList<>();

        for (MultipartFile file : files) {

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            metadata.setContentType(file.getContentType());

            String uploadPath = makeFolder(rootType);
            try {
                String fileName = uploadPath + "/" + UUID.randomUUID() + "_" + file.getOriginalFilename();
                amazonS3.putObject(bucketName, fileName, file.getInputStream(), metadata);
                paths.add(String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName,location,fileName));

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return paths;
    }
}