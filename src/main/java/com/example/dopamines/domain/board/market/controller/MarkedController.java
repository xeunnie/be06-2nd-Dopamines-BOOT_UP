package com.example.dopamines.domain.board.market.controller;

import com.example.dopamines.domain.board.market.model.dto.MarketBoardDTO.Response;
import com.example.dopamines.domain.board.market.service.MarkedService;
import com.example.dopamines.domain.user.model.entity.User;
import com.example.dopamines.global.common.BaseResponse;
import com.example.dopamines.global.common.annotation.CheckAuthentication;
import com.example.dopamines.global.security.CustomUserDetails;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/marked")
@RequiredArgsConstructor
public class MarkedController {

    private final MarkedService markedService;

    @PutMapping("/{idx}")
    @CheckAuthentication
    public ResponseEntity<BaseResponse<?>> create(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable("idx") Long idx) {
        User user = customUserDetails.getUser();
        String result = markedService.create(user, idx);
        return ResponseEntity.ok(new BaseResponse<>(result));
    }

    @GetMapping
    @CheckAuthentication
    public ResponseEntity<BaseResponse<List<Response>>> findAll(@AuthenticationPrincipal CustomUserDetails customUserDetails, Integer page, Integer size) {
        User user = customUserDetails.getUser();
        List<Response> posts = markedService.findAll(user, page, size);
        return ResponseEntity.ok(new BaseResponse(posts));
    }
}
