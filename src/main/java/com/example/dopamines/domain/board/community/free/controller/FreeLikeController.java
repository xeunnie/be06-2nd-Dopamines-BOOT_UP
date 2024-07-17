package com.example.dopamines.domain.board.community.free.controller;

import com.example.dopamines.domain.board.community.free.model.response.FreeLikeRes;
import com.example.dopamines.domain.board.community.free.service.FreeLikeService;
import com.example.dopamines.domain.user.model.entity.User;
import com.example.dopamines.global.common.BaseResponse;
import com.example.dopamines.global.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/like")
@RequiredArgsConstructor
public class FreeLikeController {
    private final FreeLikeService freeLikeService;

    //1. DB 회원가입 진행
    //2. DB에서 회원가입 잘 됐는지 확인
    //3. 메일인증
    //4, 로그인 진행 -> JWT 토큰 발급
    //5. JWT 토큰을 POSTMAN에 심어주기
    //6. 그 후 컨트롤러 요청
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<BaseResponse<?>> create(@AuthenticationPrincipal CustomUserDetails customUserDetails, Long idx){
        User user = customUserDetails.getUser();
        String result = freeLikeService.create(user,idx);
        return ResponseEntity.ok(new BaseResponse<>(result));

    }


}
