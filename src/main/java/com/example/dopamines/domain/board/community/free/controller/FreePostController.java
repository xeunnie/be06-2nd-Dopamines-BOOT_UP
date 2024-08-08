package com.example.dopamines.domain.board.community.free.controller;

import com.example.dopamines.domain.board.community.free.model.request.FreePostReq;
import com.example.dopamines.domain.board.community.free.model.request.FreePostUpdateReq;
import com.example.dopamines.domain.board.community.free.model.response.FreePostReadRes;
import com.example.dopamines.domain.board.community.free.model.response.FreePostRes;
import com.example.dopamines.domain.board.community.free.service.FreePostService;
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
@RequestMapping("/free/post")
@RequiredArgsConstructor
public class FreePostController {

    private String rootType ="FREEBOARD";
    private final FreePostService freePostService;
    private final CloudFileUploadService cloudFileUploadService;

    @PostMapping("/create")
    public ResponseEntity<BaseResponse<?>> create(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody FreePostReq req){
        User user = customUserDetails.getUser();
//        System.out.println(user);
//        List<String> urlLists = cloudFileUploadService.uploadImages(files, rootType);
        String result = freePostService.create(user, req, req.getImages());
        return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponse<>(result));
    }

    @PostMapping("/upload-image")
    public ResponseEntity<BaseResponse<?>> uploadImage(@RequestPart MultipartFile[] files) {
        List<String> savedFileName = cloudFileUploadService.uploadImages(files, rootType);
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(savedFileName));
    }

    @GetMapping("/read")
    public ResponseEntity<BaseResponse<?>> read(@AuthenticationPrincipal CustomUserDetails customUserDetails, Long idx){
        User user = customUserDetails.getUser();
        FreePostReadRes response = freePostService.read(idx);
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(response));
    }

    @GetMapping("/read-all")
    public ResponseEntity<BaseResponse<?>> readAll(@AuthenticationPrincipal CustomUserDetails customUserDetails,Integer page, Integer size){
        User user = customUserDetails.getUser();
        List<FreePostRes> response = freePostService.readAll(page,size);
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(response));
    }

    @PutMapping("/update")
    public ResponseEntity<BaseResponse<?>> update(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestPart FreePostUpdateReq req){
        User user = customUserDetails.getUser();
        FreePostRes response = freePostService.update(user,req, req.getImages());
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(response));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<BaseResponse<?>> delete(@AuthenticationPrincipal CustomUserDetails customUserDetails, Long idx){
        User user = customUserDetails.getUser();
        freePostService.delete(user,idx);
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(BaseResponseStatus.SUCCESS_NO_CONTENT));
    }

    @GetMapping("/search")
    public ResponseEntity<BaseResponse<?>> search(@AuthenticationPrincipal CustomUserDetails customUserDetails,Integer page, Integer size,String keyword){
        User user = customUserDetails.getUser();
        List<FreePostRes> response = freePostService.search(page,size,keyword);
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(response));
    }
}
