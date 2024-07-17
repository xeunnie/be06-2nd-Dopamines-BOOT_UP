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

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<BaseResponse<?>> create(@AuthenticationPrincipal CustomUserDetails customUserDetails, Long idx){
        User user = customUserDetails.getUser();
        String result = freeLikeService.create(user,idx);
        return ResponseEntity.ok(new BaseResponse<>(result));

    }

    // TODO : user 정보 연결 시, delete 필요 없을거 같음 - 참고 day13 실습
    @RequestMapping(method = RequestMethod.GET, value = "/delete")
    public ResponseEntity<FreeLikeRes> delete(Long idx){
        FreeLikeRes res = freeLikeService.delete(idx);
        return ResponseEntity.ok(res);
    }

}
