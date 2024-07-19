package com.example.dopamines.domain.board.community.open.controller;

import com.example.dopamines.domain.board.community.open.service.OpenLikeService;
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
@RequestMapping("/open/like")
@RequiredArgsConstructor
public class OpenLikeController {
    private final OpenLikeService openLikeService;

    @GetMapping("/open-post")
    public ResponseEntity<BaseResponse<?>> createOpenPostLike(@AuthenticationPrincipal CustomUserDetails customUserDetails, Long idx){
        User user = customUserDetails.getUser();
        String result = openLikeService.createOpenPostLike(user,idx);

        return ResponseEntity.ok(new BaseResponse<>(result));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/comment")
    public ResponseEntity<BaseResponse<?>> createCommentLike(@AuthenticationPrincipal CustomUserDetails customUserDetails, Long idx){
        User user = customUserDetails.getUser();
        String result = openLikeService.createCommentLike(user,idx);

        return ResponseEntity.ok(new BaseResponse<>(result));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/recomment")
    public ResponseEntity<BaseResponse<?>> createRecommentLike(@AuthenticationPrincipal CustomUserDetails customUserDetails, Long idx){
        User user = customUserDetails.getUser();
        String result = openLikeService.createRecommentLike(user,idx);

        return ResponseEntity.ok(new BaseResponse<>(result));
    }
}
