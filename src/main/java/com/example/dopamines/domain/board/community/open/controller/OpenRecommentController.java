package com.example.dopamines.domain.board.community.open.controller;

import com.example.dopamines.domain.board.community.open.model.request.OpenRecommentReq;
import com.example.dopamines.domain.board.community.open.model.request.OpenRecommentUpdateReq;
import com.example.dopamines.domain.board.community.open.service.OpenRecommentService;
import com.example.dopamines.domain.user.model.entity.User;
import com.example.dopamines.global.common.BaseResponse;
import com.example.dopamines.global.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/open/recomment")
@RequiredArgsConstructor
public class OpenRecommentController {
    private final OpenRecommentService openRecommentService;

    @PostMapping("/create")
    public ResponseEntity<BaseResponse<?>> create(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody OpenRecommentReq req){
        User user = customUserDetails.getUser();
        String response = openRecommentService.create(user,req);

        return ResponseEntity.ok(new BaseResponse<>(response));
    }

    @PutMapping("/update")
    public ResponseEntity<BaseResponse<?>> update(@AuthenticationPrincipal CustomUserDetails customUserDetails,@RequestBody OpenRecommentUpdateReq req){
        User user = customUserDetails.getUser();
        String response = openRecommentService.update(user,req);

        return ResponseEntity.ok(new BaseResponse<>(response));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<BaseResponse<?>> delete(@AuthenticationPrincipal CustomUserDetails customUserDetails,Long idx){
        User user = customUserDetails.getUser();
        String response = openRecommentService.delete(user,idx);

        return ResponseEntity.ok(new BaseResponse<>(response));
    }
}

