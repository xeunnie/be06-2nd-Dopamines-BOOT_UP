package com.example.dopamines.domain.board.notice.controller;

import com.example.dopamines.domain.board.notice.model.entity.Notice;
import com.example.dopamines.domain.board.notice.model.request.NoticeRequestDto;
import com.example.dopamines.domain.board.notice.model.response.NoticeResponseDto;
import com.example.dopamines.domain.board.notice.service.NoticeService;
import com.example.dopamines.global.common.BaseResponse;
import com.example.dopamines.global.common.BaseResponseStatus;
import com.example.dopamines.global.common.annotation.CheckAdmin;
import com.example.dopamines.global.common.annotation.CheckAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/notices")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @CheckAuthentication
    @CheckAdmin("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<BaseResponse<Notice>> createNotice(@RequestBody NoticeRequestDto noticeRequestDto) {
        Notice createdNotice = noticeService.saveNotice(noticeRequestDto).toEntity();
        return ResponseEntity.ok(new BaseResponse<>(createdNotice));
    }

    @GetMapping("/{id}")
    @CheckAdmin("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<BaseResponse<NoticeResponseDto>> getNotice(@PathVariable Long id) {
        Optional<NoticeResponseDto> noticeOptional = Optional.ofNullable(noticeService.getNotice(id));
        if (noticeOptional.isPresent()) {
            BaseResponse<NoticeResponseDto> response = new BaseResponse<>(noticeOptional.get());
            return ResponseEntity.ok(response);
        } else {
            BaseResponse<NoticeResponseDto> response = new BaseResponse<>(false, BaseResponseStatus.NOTICE_NOT_FOUND.getMessage(), BaseResponseStatus.NOTICE_NOT_FOUND.getCode(), null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @CheckAuthentication
    @CheckAdmin("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> deleteNotice(@PathVariable Long id) {
        noticeService.deleteNotice(id);
        return ResponseEntity.ok(new BaseResponse<>(BaseResponseStatus.NOTICE_ERROR_CONVENTION));
    }

    @CheckAuthentication
    @CheckAdmin("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<Notice>> updateNotice(@PathVariable Long id, @RequestBody NoticeRequestDto noticeRequestDto) {
        Notice updatedNotice = noticeService.updateNotice(id, noticeRequestDto).toEntity();
        return ResponseEntity.ok(new BaseResponse<>(updatedNotice));
    }

    @CheckAuthentication
    @CheckAdmin("hasRole('ADMIN')")
    @GetMapping("/private")
    public ResponseEntity<BaseResponse<Page<Notice>>> getAllPrivateNotices(Pageable pageable) {
        Page<Notice> notices = noticeService.getAllPrivateNotices(pageable);
        return ResponseEntity.ok(new BaseResponse<>(notices));
    }

    @GetMapping("/public")
    @CheckAdmin("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<BaseResponse<Page<Notice>>> getAllPublicNotices(Pageable pageable) {
        Page<Notice> notices = noticeService.getAllPublicNotices(pageable);
        return ResponseEntity.ok(new BaseResponse<>(notices));
    }

    @GetMapping("/category")
    @CheckAdmin("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<BaseResponse<Page<Notice>>> getNoticesByCategory(@RequestParam String category, Pageable pageable) {
        Page<Notice> notices = noticeService.getNoticesByCategory(category, pageable);
        return ResponseEntity.ok(new BaseResponse<>(notices));
    }

    @GetMapping("/date")
    @CheckAdmin("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<BaseResponse<Page<Notice>>> getNoticesByDateRange(@RequestParam String startDate, @RequestParam String endDate, Pageable pageable) {
        LocalDateTime start = LocalDateTime.parse(startDate);
        LocalDateTime end = LocalDateTime.parse(endDate);
        Page<Notice> notices = noticeService.getNoticesByDateRange(start, end, pageable);
        return ResponseEntity.ok(new BaseResponse<>(notices));
    }
}