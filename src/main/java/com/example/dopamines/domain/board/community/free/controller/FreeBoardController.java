package com.example.dopamines.domain.board.community.free.controller;

import com.example.dopamines.domain.board.community.free.model.request.FreeBoardReq;
import com.example.dopamines.domain.board.community.free.model.request.FreeBoardUpdateReq;
import com.example.dopamines.domain.board.community.free.model.response.FreeBoardReadRes;
import com.example.dopamines.domain.board.community.free.model.response.FreeBoardRes;
import com.example.dopamines.domain.board.community.free.service.FreeBoardService;
import com.example.dopamines.domain.user.model.entity.User;
import com.example.dopamines.global.common.BaseResponse;
import com.example.dopamines.global.infra.s3.CloudFileUploadService;
import com.example.dopamines.global.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/freeboard")
@RequiredArgsConstructor
public class FreeBoardController {
    private final FreeBoardService freeBoardService;
    private final CloudFileUploadService cloudFileUploadService;
    @RequestMapping(method = RequestMethod.POST, value = "/create")
    public ResponseEntity<BaseResponse<?>> create(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestPart FreeBoardReq req, @RequestPart MultipartFile[] files){
        User user = customUserDetails.getUser();
        String rootType ="FREEBOARD";
        List<String> urlLists = cloudFileUploadService.uploadImages(files, rootType);
        String result = freeBoardService.create(user, req, urlLists);
        return ResponseEntity.ok(new BaseResponse<>(result));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/read")
    public ResponseEntity<BaseResponse<?>> read(@AuthenticationPrincipal CustomUserDetails customUserDetails, Long idx){
        User user = customUserDetails.getUser();
        FreeBoardReadRes response = freeBoardService.read(idx);

        return ResponseEntity.ok(new BaseResponse<>(response));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/read-all")
    public ResponseEntity<BaseResponse<?>> readAll(@AuthenticationPrincipal CustomUserDetails customUserDetails,Integer page, Integer size){
        User user = customUserDetails.getUser();
        List<FreeBoardRes> response = freeBoardService.readAll(page,size);
        return ResponseEntity.ok(new BaseResponse<>(response));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/update")
    public ResponseEntity<BaseResponse<?>> update(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody FreeBoardUpdateReq req){
        User user = customUserDetails.getUser();
        FreeBoardRes response = freeBoardService.update(user,req);
        return ResponseEntity.ok(new BaseResponse<>(response));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/delete")
    public ResponseEntity<BaseResponse<?>> delete(@AuthenticationPrincipal CustomUserDetails customUserDetails, Long idx){
        User user = customUserDetails.getUser();
        String response = freeBoardService.delete(user,idx);
        return ResponseEntity.ok(new BaseResponse<>(response));
    }
}
