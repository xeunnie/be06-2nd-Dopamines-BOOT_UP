package com.example.dopamines.domain.board.community.free.controller;

import com.example.dopamines.domain.board.community.free.model.request.FreeBoardReq;
import com.example.dopamines.domain.board.community.free.model.request.UpdateFreeBoardReq;
import com.example.dopamines.domain.board.community.free.model.response.FreeBoardReadRes;
import com.example.dopamines.domain.board.community.free.model.response.FreeBoardRes;
import com.example.dopamines.domain.board.community.free.service.FreeBoardService;
import com.example.dopamines.domain.user.model.entity.User;
import com.example.dopamines.global.common.BaseResponse;
import com.example.dopamines.global.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/freeboard")
@RequiredArgsConstructor
public class FreeBoardController {
    private final FreeBoardService freeBoardService;

    @RequestMapping(method = RequestMethod.POST, value = "/create")
    public ResponseEntity<BaseResponse<?>> create(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody FreeBoardReq req){
        User user = customUserDetails.getUser();
        String result = freeBoardService.create(user,req);
        return ResponseEntity.ok(new BaseResponse<>(result));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/read")
    public ResponseEntity<BaseResponse<?>> read(@AuthenticationPrincipal CustomUserDetails customUserDetails, Long idx){
        User user = customUserDetails.getUser();
        FreeBoardReadRes response = freeBoardService.read(user,idx);

        return ResponseEntity.ok(new BaseResponse<>(response));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/read-all")
    public ResponseEntity<List<FreeBoardRes>> readAll(Integer page, Integer size){
        List<FreeBoardRes> response = freeBoardService.readAll(page,size);
        return ResponseEntity.ok(response);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/update")
    public ResponseEntity<FreeBoardRes> update(@RequestBody UpdateFreeBoardReq req){
        FreeBoardRes response = freeBoardService.update(req);
        return ResponseEntity.ok(response);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/delete")
    public ResponseEntity<String> delete(Long idx){
        freeBoardService.delete(idx);
        return ResponseEntity.ok(idx+"번의 게시글이 삭제되었습니다.");
    }
}
