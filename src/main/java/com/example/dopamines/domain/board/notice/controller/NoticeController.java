package com.example.dopamines.domain.board.notice.controller;

import com.example.dopamines.domain.board.notice.model.entity.Notice;
import com.example.dopamines.domain.board.notice.model.request.NoticeReq;
import com.example.dopamines.domain.board.notice.model.response.NoticeRes;
import com.example.dopamines.domain.board.notice.service.NoticeService;
import com.example.dopamines.global.common.BaseResponse;
import com.example.dopamines.global.common.BaseResponseStatus;
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

    // 공지사항 생성
    @PostMapping
    public ResponseEntity<BaseResponse<NoticeRes>> createNotice(@RequestBody NoticeReq req) {
        BaseResponse<NoticeRes> createdNotice = noticeService.saveNotice(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponse<>(createdNotice).getResult());
    }

    // 공지사항 조회
    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<NoticeRes>> getNotice(@PathVariable Long id) {
        Optional<NoticeRes> noticeOptional = Optional.ofNullable(noticeService.getNotice(id));
        if (noticeOptional.isPresent()) {
            BaseResponse<NoticeRes> response = new BaseResponse<>(noticeOptional.get());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            BaseResponse<NoticeRes> response = new BaseResponse<>(BaseResponseStatus.NOTICE_NOT_FOUND);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/private")
    public ResponseEntity<BaseResponse<Page<Notice>>> getAllPrivateNotices(Pageable pageable) {
        Page<Notice> notices = noticeService.getAllPrivateNotices(pageable);
        return ResponseEntity.ok(new BaseResponse<>(notices));
    }

    @GetMapping("/public")
    public ResponseEntity<BaseResponse<Page<Notice>>> getAllPublicNotices(Pageable pageable) {
        Page<Notice> notices = noticeService.getAllPublicNotices(pageable);
        return ResponseEntity.ok(new BaseResponse<>(notices));
    }

    @GetMapping("/category")
    public ResponseEntity<BaseResponse<Page<Notice>>> getNoticesByCategory(@RequestParam String category, Pageable pageable) {
        Page<Notice> notices = noticeService.getNoticesByCategory(category, pageable);
        return ResponseEntity.ok(new BaseResponse<>(notices));
    }

    @GetMapping("/date")
    public ResponseEntity<BaseResponse<Page<Notice>>> getNoticesByDateRange(@RequestParam String startDate, @RequestParam String endDate, Pageable pageable) {
        LocalDateTime start = LocalDateTime.parse(startDate);
        LocalDateTime end = LocalDateTime.parse(endDate);
        Page<Notice> notices = noticeService.getNoticesByDateRange(start, end, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(notices));
    }

    // 공지사항 검색
    @GetMapping("/notices/criteria")
    public Page<Notice> findNoticesByCriteria(
            @RequestParam(required = false) Boolean isPrivate,
            @RequestParam(required = false) String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return noticeService.findNoticesByCriteria(isPrivate, category, page, size);
    }

    @GetMapping("/notices/search")
    public Page<Notice> findNoticesByTitleAndContent(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String content,
            Pageable pageable) {
        return noticeService.findNoticesByTitleAndContent(title, content, pageable);
    }

    // 공지사항 수정
    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<Notice>> updateNotice(@PathVariable Long id, @RequestBody NoticeReq noticeRequestDto) {
        Notice updatedNotice = noticeService.updateNotice(id, noticeRequestDto).toEntity();
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(updatedNotice));
    }

    // 공지사항 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> deleteNotice(@PathVariable Long id) {
        noticeService.deleteNotice(id);
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(BaseResponseStatus.SUCCESS_NO_CONTENT));
    }
}