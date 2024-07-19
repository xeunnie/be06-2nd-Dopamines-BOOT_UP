package com.example.dopamines.domain.board.community.free.controller;

import com.example.dopamines.domain.board.community.free.service.FreeLikeService;
import com.example.dopamines.domain.user.model.entity.User;
import com.example.dopamines.global.common.BaseResponse;
import com.example.dopamines.global.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/free/like")
@RequiredArgsConstructor
public class FreeLikeController {
    private final FreeLikeService freeLikeService;

    @GetMapping("/post")
    public ResponseEntity<BaseResponse<?>> createFreePostLike(@AuthenticationPrincipal CustomUserDetails customUserDetails, Long idx){
        User user = customUserDetails.getUser();
        String result = freeLikeService.createFreePostLike(user,idx);

        return ResponseEntity.ok(new BaseResponse<>(result));
    }

    @GetMapping("/comment")
    public ResponseEntity<BaseResponse<?>> createCommentLike(@AuthenticationPrincipal CustomUserDetails customUserDetails, Long idx){
        User user = customUserDetails.getUser();
        String result = freeLikeService.createCommentLike(user,idx);

        return ResponseEntity.ok(new BaseResponse<>(result));
    }

    @GetMapping("/recomment")
    public ResponseEntity<BaseResponse<?>> createRecommentLike(@AuthenticationPrincipal CustomUserDetails customUserDetails, Long idx){
        User user = customUserDetails.getUser();
        String result = freeLikeService.createRecommentLike(user,idx);

        return ResponseEntity.ok(new BaseResponse<>(result));
    }
}
