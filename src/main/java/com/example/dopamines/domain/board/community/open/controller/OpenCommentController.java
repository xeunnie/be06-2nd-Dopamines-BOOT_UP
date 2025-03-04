package com.example.dopamines.domain.board.community.open.controller;

import com.example.dopamines.domain.board.community.open.model.request.OpenCommentReq;
import com.example.dopamines.domain.board.community.open.model.request.OpenCommentUpdateReq;
import com.example.dopamines.domain.board.community.open.service.OpenCommentService;
import com.example.dopamines.domain.user.model.entity.User;
import com.example.dopamines.global.common.BaseResponse;
import com.example.dopamines.global.common.BaseResponseStatus;
import com.example.dopamines.global.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/open/comment")
@RequiredArgsConstructor
public class OpenCommentController {
    private final OpenCommentService openCommentService;

    @PostMapping("/create")
    public ResponseEntity<BaseResponse<?>> create(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody OpenCommentReq req){
        User user = customUserDetails.getUser();
        String response = openCommentService.create(user,req);
        return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponse<>(response));
    }

    // 내가 쓴 댓글 조회
//    @RequestMapping(method = RequestMethod.GET, value = "/read")
//    public ResponseEntity<BaseResponse<?>> read(@AuthenticationPrincipal CustomUserDetails customUserDetails){
//        User user = customUserDetails.getUser();
//        List<OpenCommentReadRes> response = openCommentService.read(user);
//
//        return ResponseEntity.ok(new BaseResponse<>(response));
//    }

    @PutMapping("/update")
    public ResponseEntity<BaseResponse<?>> update(@AuthenticationPrincipal CustomUserDetails customUserDetails,@RequestBody OpenCommentUpdateReq req){
        User user = customUserDetails.getUser();
        String response = openCommentService.update(user,req);
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(response));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<BaseResponse<?>> delete(@AuthenticationPrincipal CustomUserDetails customUserDetails,Long idx){
        User user = customUserDetails.getUser();
        String response = openCommentService.delete(user,idx);
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(BaseResponseStatus.SUCCESS_NO_CONTENT));
    }
}
