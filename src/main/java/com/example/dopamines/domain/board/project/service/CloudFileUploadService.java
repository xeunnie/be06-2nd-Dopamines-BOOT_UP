//package com.example.dopamines.domain.board.project.service;
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
//public class CloudFileUploadService implements FileUploadService{
//    @Value("${cloud.aws.s3.bucket}")
//    private String bucketName;
//    private final AmazonS3 amazonS3;
//
//    public String makeFolder(String boardType) {
//        String folderPath = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
//
//        folderPath = boardType + "/" + folderPath;
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
//    public String upload(MultipartFile[] files, String boardType) {
//        String fileName = "";
//        ObjectMetadata metadata = new ObjectMetadata();
//        for (MultipartFile file : files) {
//            metadata.setContentLength(file.getSize());
//            metadata.setContentType(file.getContentType());
//
//            String uploadPath = makeFolder(boardType);
//
//            try {
//                fileName = uploadPath + "/" + UUID.randomUUID() + "_" + file.getOriginalFilename();
//
//                amazonS3.putObject(bucketName, fileName, file.getInputStream(), metadata);
//
//
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }
//
//        return "https://dopamines-bootup.s3.ap-northeast-2.amazonaws.com/" + fileName;
//    }
//}
