package com.example.dopamines.domain.board.project.controller;

import static com.example.dopamines.global.common.BaseResponseStatus.UNAUTHORIZED_ACCESS;

import com.example.dopamines.domain.board.project.model.request.ProjectPostReq;
import com.example.dopamines.domain.board.project.model.request.ProjectPostUpdateReq;
import com.example.dopamines.domain.board.project.model.response.ProjectPostRes;
import com.example.dopamines.domain.board.project.model.response.ProjectPostReadRes;
import com.example.dopamines.domain.board.project.model.response.ProjectPostTeamListRes;
import com.example.dopamines.domain.board.project.service.ProjectPostService;
import com.example.dopamines.domain.user.model.entity.User;
import com.example.dopamines.domain.user.service.UserService;
import com.example.dopamines.global.common.BaseResponse;
import com.example.dopamines.global.common.BaseResponseStatus;
import com.example.dopamines.global.common.annotation.CheckAuthentication;
import com.example.dopamines.global.infra.s3.CloudFileUploadService;
import com.example.dopamines.global.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/project")
@RequiredArgsConstructor
public class ProjectPostController {

    private final UserService userService;
    private String rootType = "PROJECT";
    private String userAuth = "";
    private final ProjectPostService projectBoardService;
    private final CloudFileUploadService cloudFileUploadService;

    @PostMapping("/upload-image")
    public ResponseEntity<BaseResponse<?>> uploadImage(@RequestPart MultipartFile file) {
        String savedFileName = cloudFileUploadService.upload(file, rootType);
        System.out.println("savedFileName: " + savedFileName);
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(savedFileName));
    }

    @PostMapping("/create")
    public ResponseEntity<BaseResponse<?>> create(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody ProjectPostReq req) {
        System.out.println(req);
        System.out.println(req.getImage());
        System.out.println(userDetails.getUser().getName());
        BaseResponse<ProjectPostRes> response = projectBoardService.create(req, req.getImage());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/team-list")
    public ResponseEntity<BaseResponse<?>> getTeamList(@RequestParam Integer courseNum) {
        System.out.println("기수: " + courseNum);
        List<ProjectPostTeamListRes> response = userService.getTeamList(courseNum);
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(response));
    }

    @GetMapping("/read")
    public ResponseEntity<BaseResponse<ProjectPostReadRes>> read(@RequestParam Long idx) {
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
        if (userDetails != null) {
            userAuth = userDetails.getUser().getRole();
        } else {
            userAuth = "ROLE_TEMPORARY_USER";
        }

        BaseResponse<List<ProjectPostReadRes>> response = projectBoardService.readAll(userAuth);
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