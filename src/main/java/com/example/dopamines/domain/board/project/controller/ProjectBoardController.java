package com.example.dopamines.domain.board.project.controller;

import static com.example.dopamines.global.common.BaseResponseStatus.UNAUTHORIZED_ACCESS;

import com.example.dopamines.domain.board.project.model.request.ProjectBoardReq;
import com.example.dopamines.domain.board.project.model.request.UpdateProjectBoardReq;
import com.example.dopamines.domain.board.project.model.response.ProjectBoardRes;
import com.example.dopamines.domain.board.project.model.response.ReadProjectBoardRes;
import com.example.dopamines.domain.board.project.service.ProjectBoardService;
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
public class ProjectBoardController {

    private String rootType = "PROJECT";
    private final ProjectBoardService projectBoardService;

    private final CloudFileUploadService cloudFileUploadService;

    @RequestMapping(method = RequestMethod.POST, value = "/create")
    public ResponseEntity<ProjectBoardRes> create(@RequestPart ProjectBoardReq req, @RequestPart MultipartFile[] files) {

        List<String> savedFileName = cloudFileUploadService.uploadImages(files, rootType);
        ProjectBoardRes response = projectBoardService.create(req, savedFileName.get(0));

        return ResponseEntity.ok(response);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/read")
    public ResponseEntity<ReadProjectBoardRes> read(Long idx) {

        ReadProjectBoardRes response = projectBoardService.read(idx);

        if(response != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/read-by-course-num")
    public ResponseEntity<List<ReadProjectBoardRes>> readByCourseNum(Long courseNum) {

        List<ReadProjectBoardRes> response = projectBoardService.readByCourseNum(courseNum);

        if(response != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @CheckAuthentication
    @RequestMapping(method = RequestMethod.GET, value = "/read-all")
    public ResponseEntity<?> readAll(@AuthenticationPrincipal CustomUserDetails userDetails) {
        boolean hasAdminRole = userDetails.getAuthorities().stream().anyMatch(authority->authority.getAuthority().equals("ROLE_ADMIN"));
        if (!hasAdminRole) {
            return ResponseEntity.badRequest().body(new BaseResponse(UNAUTHORIZED_ACCESS));
        }

        List<ReadProjectBoardRes> response = projectBoardService.readAll();
        if(response != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }


    @RequestMapping(method = RequestMethod.POST, value = "/update")
    public ResponseEntity<ReadProjectBoardRes> update(@RequestPart UpdateProjectBoardReq req, @RequestPart MultipartFile[] files) {
        ReadProjectBoardRes response = null;
        if(!req.getSourceUrl().isEmpty()) {
             response = projectBoardService.update(req, req.getSourceUrl());
        } else {
            List<String> savedFileName = cloudFileUploadService.uploadImages(files);
            response = projectBoardService.update(req, savedFileName.get(0));
        }

        return ResponseEntity.ok(response);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/delete")
    public ResponseEntity<String> delete(Long idx) {

        projectBoardService.delete(idx);

        return ResponseEntity.ok("삭제가 완료되었습니다.");
    }
}