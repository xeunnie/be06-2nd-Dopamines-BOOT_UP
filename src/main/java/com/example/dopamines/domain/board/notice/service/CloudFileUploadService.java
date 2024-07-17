//package com.example.dopamines.domain.board.notice.service;
//
//import com.amazonaws.services.s3.AmazonS3;
//import com.amazonaws.services.s3.model.ObjectMetadata;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.File;
//import java.io.IOException;
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//import java.util.UUID;
//
//@Service
//@RequiredArgsConstructor
//public class CloudFileUploadService implements FileUploadService {
//    @Value("${cloud.aws.s3.bucket}")
//    private String bucketName;
//    private final AmazonS3 amazonS3;
//
//    public String makeFolder() {
//        String folderPath = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
//
//        File uploadPathFolder = new File(folderPath);
//        if (!uploadPathFolder.exists()) {
//            uploadPathFolder.mkdirs();
//        }
//
//        return folderPath;
//    }
//
//    @Override
//    public String upload(MultipartFile file) {
//        ObjectMetadata metadata = new ObjectMetadata();
//        metadata.setContentLength(file.getSize());
//        metadata.setContentType(file.getContentType());
//
//        String uploadPath = makeFolder();
//        try {
//            String fileName = uploadPath + "/" + UUID.randomUUID() + "_" + file.getOriginalFilename();
//            amazonS3.putObject(bucketName, fileName, file.getInputStream(), metadata);
//
//            return "https://dopamines-bootup.s3.ap-northeast-2.amazonaws.com/" + fileName;
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//}
