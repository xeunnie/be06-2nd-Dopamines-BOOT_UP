package com.example.dopamines.domain.board.project.controller;

import static com.example.dopamines.global.common.BaseResponseStatus.UNAUTHORIZED_ACCESS;

import com.example.dopamines.domain.board.project.model.request.ProjectPostReq;
import com.example.dopamines.domain.board.project.model.request.ProjectPostUpdateReq;
import com.example.dopamines.domain.board.project.model.response.ProjectPostRes;
import com.example.dopamines.domain.board.project.model.response.ProjectPostReadRes;
import com.example.dopamines.domain.board.project.service.ProjectPostService;
import com.example.dopamines.domain.user.service.UserService;
import com.example.dopamines.global.common.BaseResponse;
import com.example.dopamines.global.common.BaseResponseStatus;
import com.example.dopamines.global.common.annotation.CheckAuthentication;
import com.example.dopamines.global.infra.s3.CloudFileUploadService;
import com.example.dopamines.global.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/project")
@RequiredArgsConstructor
public class ProjectPostController {

    private final UserService userService;
    private String rootType = "PROJECT";
    private final ProjectPostService projectBoardService;
    private final CloudFileUploadService cloudFileUploadService;

    @PostMapping("/upload-image")
    public ResponseEntity<BaseResponse<?>> uploadImage(@RequestPart MultipartFile[] files) {
        List<String> savedFileName = cloudFileUploadService.uploadImages(files, rootType);
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(savedFileName));
    }

    @PostMapping("/create")
    public ResponseEntity<BaseResponse<ProjectPostRes>> create(@RequestPart ProjectPostReq req) {
        System.out.println(req);
        BaseResponse<ProjectPostRes> response = projectBoardService.create(req, req.getImages().get(0));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

//    @GetMapping("/team-list")
//    public ResponseEntity<BaseResponse<?>> getTeamList(Long idx) {
//        System.out.println("기수: " + idx);
////        BaseResponse<ProjectPostTeamListRes> response = userService.getTeamList(idx);
//        userService.getTeamList();
//        return ResponseEntity.status(HttpStatus.OK).body(response);
//    }

    @GetMapping("/team-list")
    public void getTeamList(Long idx) {
        System.out.println("기수: " + idx);
//        BaseResponse<ProjectPostTeamListRes> response = userService.getTeamList(idx);
        //userService.getTeamList();
    }

    @GetMapping("/read")
    public ResponseEntity<BaseResponse<ProjectPostReadRes>> read(Long idx) {
        BaseResponse<ProjectPostReadRes> response = projectBoardService.read(idx);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

//    @GetMapping("/read-by-course-num")
//    public ResponseEntity<BaseResponse<List<ProjectPostReadRes>>> readByCourseNum(Integer courseNum) {
//        BaseResponse<List<ProjectPostReadRes>> response = projectBoardService.readByCourseNum(courseNum);
//        return ResponseEntity.status(HttpStatus.OK).body(response);
//    }

//    @CheckAuthentication
    @GetMapping("/read-all")
    public ResponseEntity<BaseResponse<?>> readAll(@AuthenticationPrincipal CustomUserDetails userDetails) {
        BaseResponse<List<ProjectPostReadRes>> response = projectBoardService.readAll();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @PutMapping("/update")
    public ResponseEntity<BaseResponse<ProjectPostReadRes>> update(@RequestPart ProjectPostUpdateReq req, @RequestPart(required = false) MultipartFile[] files) {
        ProjectPostReadRes response = null;
        if(!req.getSourceUrl().isEmpty()) {
            response = projectBoardService.update(req, req.getSourceUrl());
        } else {
            List<String> savedFileName = cloudFileUploadService.uploadImages(files, rootType);
            response = projectBoardService.update(req, savedFileName.get(0));
        }

        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(response));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<BaseResponse<Void>> delete(Long idx) {
        projectBoardService.delete(idx);
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(BaseResponseStatus.SUCCESS_NO_CONTENT));
    }
}