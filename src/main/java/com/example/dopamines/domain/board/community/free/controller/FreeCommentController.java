package com.example.dopamines.domain.board.community.free.controller;

import com.example.dopamines.domain.board.community.free.model.request.FreeCommentReq;
import com.example.dopamines.domain.board.community.free.model.request.FreeCommentUpdateReq;
import com.example.dopamines.domain.board.community.free.model.response.FreeCommentReadRes;
import com.example.dopamines.domain.board.community.free.service.FreeCommentService;
import com.example.dopamines.domain.user.model.entity.User;
import com.example.dopamines.global.common.BaseResponse;
import com.example.dopamines.global.common.BaseResponseStatus;
import com.example.dopamines.global.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/free/comment")
@RequiredArgsConstructor
public class FreeCommentController {
    private final FreeCommentService freeCommentService;

    @PostMapping("/create")
    public ResponseEntity<BaseResponse<?>> create(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody FreeCommentReq req){
        System.out.println(req.getFreePostIdx());
        System.out.println(req.getContent());
        User user = customUserDetails.getUser();
        FreeCommentReadRes response = freeCommentService.create(user,req);
        return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponse<>(response));
    }

    @GetMapping
    public ResponseEntity<BaseResponse<?>> findAll(Long idx, Integer page, Integer size) {
        List<FreeCommentReadRes> res = freeCommentService.findAllWithPage(idx, page, size);
        return ResponseEntity.ok(new BaseResponse<>(res));
    }

    @PutMapping("/update")
    public ResponseEntity<BaseResponse<?>> update(@AuthenticationPrincipal CustomUserDetails customUserDetails,@RequestBody FreeCommentUpdateReq req){
        User user = customUserDetails.getUser();
        String response = freeCommentService.update(user,req);
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(response));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<BaseResponse<?>> delete(@AuthenticationPrincipal CustomUserDetails customUserDetails,Long idx){
        User user = customUserDetails.getUser();
        String response = freeCommentService.delete(user,idx);
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(BaseResponseStatus.SUCCESS_NO_CONTENT));
    }
}
