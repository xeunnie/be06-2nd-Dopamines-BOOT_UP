package com.example.dopamines.domain.board.community.free.controller;

import com.example.dopamines.domain.board.community.free.model.request.FreeCommentReq;
import com.example.dopamines.domain.board.community.free.model.request.FreeCommentUpdateReq;
import com.example.dopamines.domain.board.community.free.model.request.FreeRecommentReq;
import com.example.dopamines.domain.board.community.free.model.request.FreeRecommentUpdateReq;
import com.example.dopamines.domain.board.community.free.model.response.FreeCommentReadRes;
import com.example.dopamines.domain.board.community.free.service.FreeRecommentService;
import com.example.dopamines.domain.user.model.entity.User;
import com.example.dopamines.global.common.BaseResponse;
import com.example.dopamines.global.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/free/recomment")
@RequiredArgsConstructor
public class FreeRecommentController {
    private final FreeRecommentService freeRecommentService;

    @PostMapping("/create")
    public ResponseEntity<BaseResponse<?>> create(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody FreeRecommentReq req){
        User user = customUserDetails.getUser();
        String response = freeRecommentService.create(user,req);

        return ResponseEntity.ok(new BaseResponse<>(response));
    }

    @PutMapping("/update")
    public ResponseEntity<BaseResponse<?>> update(@AuthenticationPrincipal CustomUserDetails customUserDetails,@RequestBody FreeRecommentUpdateReq req){
        User user = customUserDetails.getUser();
        String response = freeRecommentService.update(user,req);

        return ResponseEntity.ok(new BaseResponse<>(response));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<BaseResponse<?>> delete(@AuthenticationPrincipal CustomUserDetails customUserDetails,Long idx){
        User user = customUserDetails.getUser();
        String response = freeRecommentService.delete(user,idx);

        return ResponseEntity.ok(new BaseResponse<>(response));
    }
}

