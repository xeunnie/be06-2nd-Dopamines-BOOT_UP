package com.example.dopamines.domain.board.project.controller;

import static com.example.dopamines.global.common.BaseResponseStatus.UNAUTHORIZED_ACCESS;

import com.example.dopamines.domain.board.project.model.request.ProjectPostReq;
import com.example.dopamines.domain.board.project.model.request.ProjectPostUpdateReq;
import com.example.dopamines.domain.board.project.model.response.ProjectPostRes;
import com.example.dopamines.domain.board.project.model.response.ProjectPostReadRes;
import com.example.dopamines.domain.board.project.service.ProjectPostService;
import com.example.dopamines.global.common.BaseResponse;
import com.example.dopamines.global.common.annotation.CheckAuthentication;
import com.example.dopamines.global.infra.s3.CloudFileUploadService;
import com.example.dopamines.global.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/project")
@RequiredArgsConstructor
public class ProjectPostController {

    private String rootType = "PROJECT";
    private final ProjectPostService projectBoardService;

    private final CloudFileUploadService cloudFileUploadService;

    @PostMapping("/create")
    public ResponseEntity<ProjectPostRes> create(@RequestPart ProjectPostReq req, @RequestPart MultipartFile[] files) {

        List<String> savedFileName = cloudFileUploadService.uploadImages(files, rootType);
        ProjectPostRes response = projectBoardService.create(req, savedFileName.get(0));

        return ResponseEntity.ok(response);
    }

    @GetMapping("/read")
    public ResponseEntity<ProjectPostReadRes> read(Long idx) {

        ProjectPostReadRes response = projectBoardService.read(idx);

        if(response != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/read-by-course-num")
    public ResponseEntity<List<ProjectPostReadRes>> readByCourseNum(Long courseNum) {

        List<ProjectPostReadRes> response = projectBoardService.readByCourseNum(courseNum);

        if(response != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @CheckAuthentication
    @GetMapping("/read-all")
    public ResponseEntity<?> readAll(@AuthenticationPrincipal CustomUserDetails userDetails) {
        boolean hasAdminRole = userDetails.getAuthorities().stream().anyMatch(authority->authority.getAuthority().equals("ROLE_ADMIN"));
        if (!hasAdminRole) {
            return ResponseEntity.badRequest().body(new BaseResponse(UNAUTHORIZED_ACCESS));
        }

        List<ProjectPostReadRes> response = projectBoardService.readAll();
        if(response != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }


    @PatchMapping("/update")
    public ResponseEntity<ProjectPostReadRes> update(@RequestPart ProjectPostUpdateReq req, @RequestPart MultipartFile[] files) {
        ProjectPostReadRes response = null;
        if(!req.getSourceUrl().isEmpty()) {
             response = projectBoardService.update(req, req.getSourceUrl());
        } else {
            List<String> savedFileName = cloudFileUploadService.uploadImages(files, "PROJECT"); // TODO : "PROJECT" 지우기
            response = projectBoardService.update(req, savedFileName.get(0));
        }

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(Long idx) {

        projectBoardService.delete(idx);

        return ResponseEntity.ok("삭제가 완료되었습니다.");
    }
}