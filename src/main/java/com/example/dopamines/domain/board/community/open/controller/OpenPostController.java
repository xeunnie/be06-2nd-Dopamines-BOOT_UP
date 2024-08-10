package com.example.dopamines.domain.board.community.open.controller;

import com.example.dopamines.domain.board.community.open.model.request.OpenPostReq;
import com.example.dopamines.domain.board.community.open.model.request.OpenPostUpdateReq;
import com.example.dopamines.domain.board.community.open.model.response.OpenPostReadRes;
import com.example.dopamines.domain.board.community.open.model.response.OpenPostRes;
import com.example.dopamines.domain.board.community.open.service.OpenPostService;
import com.example.dopamines.domain.user.model.entity.User;
import com.example.dopamines.global.common.BaseResponse;
import com.example.dopamines.global.common.BaseResponseStatus;
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
@RequestMapping("/open/post")
@RequiredArgsConstructor
public class OpenPostController {
    private String rootType ="FREEBOARD";
    private final OpenPostService openPostService;
    private final CloudFileUploadService cloudFileUploadService;

    @PostMapping("/create")
    public ResponseEntity<BaseResponse<?>> create(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestPart OpenPostReq req, @RequestPart MultipartFile[] files){
        User user = customUserDetails.getUser();
        List<String> urlLists = cloudFileUploadService.uploadImages(files, rootType);
        String result = openPostService.create(user, req, urlLists);
        return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponse<>(result));
    }

    @GetMapping("/read")
    public ResponseEntity<BaseResponse<?>> read(@AuthenticationPrincipal CustomUserDetails customUserDetails, Long idx){
        User user = customUserDetails.getUser();
        OpenPostReadRes response = openPostService.read(idx);
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(response));
    }

    @GetMapping("/read-all")
    public ResponseEntity<BaseResponse<?>> readAll(@AuthenticationPrincipal CustomUserDetails customUserDetails,@RequestParam Integer page, @RequestParam Integer size){
//        User user = customUserDetails.getUser();
        List<OpenPostRes> response = openPostService.readAll(page,size);
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(response));
    }

    @PutMapping("/update")
    public ResponseEntity<BaseResponse<?>> update(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestPart OpenPostUpdateReq req, @RequestPart MultipartFile[] files){
        User user = customUserDetails.getUser();
        List<String> urlLists = cloudFileUploadService.uploadImages(files, rootType);
        OpenPostRes response = openPostService.update(user,req,urlLists);
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(response));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<BaseResponse<?>> delete(@AuthenticationPrincipal CustomUserDetails customUserDetails, Long idx){
        User user = customUserDetails.getUser();
        openPostService.delete(user,idx);
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(BaseResponseStatus.SUCCESS_NO_CONTENT));
    }

    @GetMapping("/search")
    public ResponseEntity<BaseResponse<?>> search(@AuthenticationPrincipal CustomUserDetails customUserDetails,Integer page, Integer size,String keyword){
        User user = customUserDetails.getUser();
        List<OpenPostRes> response = openPostService.search(page,size,keyword);
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(response));
    }
}
