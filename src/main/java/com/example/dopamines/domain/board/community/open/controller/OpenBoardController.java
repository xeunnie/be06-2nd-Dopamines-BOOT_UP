package com.example.dopamines.domain.board.community.open.controller;

import com.example.dopamines.domain.board.community.open.model.request.OpenBoardReq;
import com.example.dopamines.domain.board.community.open.model.request.OpenBoardUpdateReq;
import com.example.dopamines.domain.board.community.open.model.response.OpenBoardReadRes;
import com.example.dopamines.domain.board.community.open.model.response.OpenBoardRes;
import com.example.dopamines.domain.board.community.open.service.OpenBoardService;
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
@RequestMapping("/openboard")
@RequiredArgsConstructor
public class OpenBoardController {
    private final OpenBoardService openBoardService;

    @RequestMapping(method = RequestMethod.POST, value = "/create")
    public ResponseEntity<BaseResponse<?>> create(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody OpenBoardReq req){
        User user = customUserDetails.getUser();
        String result = openBoardService.create(user,req);
        return ResponseEntity.ok(new BaseResponse<>(result));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/read")
    public ResponseEntity<BaseResponse<?>> read(@AuthenticationPrincipal CustomUserDetails customUserDetails, Long idx){
        User user = customUserDetails.getUser();
        OpenBoardReadRes response = openBoardService.read(idx);

        return ResponseEntity.ok(new BaseResponse<>(response));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/read-all")
    public ResponseEntity<BaseResponse<?>> readAll(@AuthenticationPrincipal CustomUserDetails customUserDetails,Integer page, Integer size){
        User user = customUserDetails.getUser();
        List<OpenBoardRes> response = openBoardService.readAll(page,size);
        return ResponseEntity.ok(new BaseResponse<>(response));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/update")
    public ResponseEntity<BaseResponse<?>> update(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody OpenBoardUpdateReq req){
        User user = customUserDetails.getUser();
        OpenBoardRes response = openBoardService.update(user,req);
        return ResponseEntity.ok(new BaseResponse<>(response));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/delete")
    public ResponseEntity<BaseResponse<?>> delete(@AuthenticationPrincipal CustomUserDetails customUserDetails, Long idx){
        User user = customUserDetails.getUser();
        String response = openBoardService.delete(user,idx);
        return ResponseEntity.ok(new BaseResponse<>(response));
    }
}
