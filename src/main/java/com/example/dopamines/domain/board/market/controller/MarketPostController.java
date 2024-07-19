package com.example.dopamines.domain.board.market.controller;

import static com.example.dopamines.global.common.BaseResponseStatus.UNAUTHORIZED_ACCESS;

import com.example.dopamines.domain.board.market.model.request.MarketCreateReq;
import com.example.dopamines.domain.board.market.model.response.MarketDetailRes;
import com.example.dopamines.domain.board.market.model.response.MarketReadRes;
import com.example.dopamines.domain.board.market.service.MarkedService;
import com.example.dopamines.domain.board.market.service.MarketService;
import com.example.dopamines.domain.user.model.entity.User;
import com.example.dopamines.global.common.BaseResponse;
import com.example.dopamines.global.common.annotation.CheckAuthentication;
import com.example.dopamines.global.infra.s3.CloudFileUploadService;
import com.example.dopamines.global.security.CustomUserDetails;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/market")
@RequiredArgsConstructor
public class MarketPostController {

    private final String BOARD_TYPE = "MARKET";
    private final MarketService marketService;
    private final MarkedService markedService;
    private final CloudFileUploadService cloudFileUploadService;


    @PostMapping
    @CheckAuthentication
    public ResponseEntity<BaseResponse<?>> create(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestPart MultipartFile[] images, @RequestPart MarketCreateReq req) {
        User user = customUserDetails.getUser();
        List<String> imagePathes = cloudFileUploadService.uploadImages(images, BOARD_TYPE);
        MarketReadRes post = marketService.add(imagePathes, req, user);

        return ResponseEntity.ok(new BaseResponse(post));
    }

    @GetMapping
    public ResponseEntity<BaseResponse<List<MarketReadRes>>> findAll(Integer page, Integer size) {
        List<MarketReadRes> posts = marketService.findAll(page, size);
        return ResponseEntity.ok(new BaseResponse(posts));
    }

    @GetMapping("/{idx}")
    @CheckAuthentication
    public ResponseEntity<BaseResponse<MarketDetailRes>> findOne(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable("idx") Long idx) {
        User user = customUserDetails.getUser();
        MarketDetailRes post = marketService.findById(idx);

        Boolean isMarked = markedService.checkMarked(user, idx);
        post.setMarked(isMarked);

        return ResponseEntity.ok(new BaseResponse(post));
    }

    @GetMapping("/search")
    public ResponseEntity<BaseResponse<List<MarketReadRes>>> search(Integer page, Integer size, String keyword, Integer minPrice, Integer maxPrice) {
        List<MarketReadRes> result = marketService.search(page, size, keyword, minPrice, maxPrice);
        return ResponseEntity.ok(new BaseResponse(result));
    }

    @PatchMapping("/{idx}/status")
    public ResponseEntity updateStatus(@PathVariable("idx") Long idx) {
        marketService.updateStatus(idx);
        return ResponseEntity.ok("");
    }

    @DeleteMapping("/{idx}")
    @CheckAuthentication
    public ResponseEntity<?> delete(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable("idx") Long idx) {
        boolean hasRoleAdmin = customUserDetails.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));

        if (!hasRoleAdmin) {
            return ResponseEntity.badRequest().body(new BaseResponse(UNAUTHORIZED_ACCESS));
        }

        marketService.delete(idx);
        return ResponseEntity.ok(new BaseResponse(""));
    }
}
