package com.example.dopamines.domain.board.notice.service;

import com.example.dopamines.domain.board.notice.model.entity.Notice;
import com.example.dopamines.domain.board.notice.model.request.NoticeRequestDto;
import com.example.dopamines.domain.board.notice.model.response.NoticeResponseDto;
import com.example.dopamines.domain.board.notice.repository.NoticeRepository;
import com.example.dopamines.global.infra.s3.CloudFileUploadService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final CloudFileUploadService fileUploadService;

    @Transactional
    public NoticeResponseDto saveNotice(NoticeRequestDto noticeRequestDto) {
        Notice notice = noticeRequestDto.toEntity();
        Notice savedNotice = noticeRepository.save(notice);
        return new NoticeResponseDto(
                savedNotice.getId(),
                savedNotice.getTitle(),
                savedNotice.getContent(),
                savedNotice.getDate(),
                savedNotice.getCategory(),
                savedNotice.isPrivate(),
                savedNotice.getImageUrls()
        );
    }

    public NoticeResponseDto getNotice(Long id) {
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notice not found"));
        return new NoticeResponseDto(
                notice.getId(),
                notice.getTitle(),
                notice.getContent(),
                notice.getDate(),
                notice.getCategory(),
                notice.isPrivate(),
                notice.getImageUrls()
        );
    }

    public NoticeResponseDto updateNotice(Long id, NoticeRequestDto noticeDetails) {
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notice not found"));
        notice.setTitle(noticeDetails.getTitle());
        notice.setContent(noticeDetails.getContent());
        notice.setCategory(noticeDetails.getCategory());
        notice.setPrivate(noticeDetails.isPrivate());
        Notice savedNotice = noticeRepository.save(notice);
        return new NoticeResponseDto(
                savedNotice.getId(),
                savedNotice.getTitle(),
                savedNotice.getContent(),
                savedNotice.getDate(),
                savedNotice.getCategory(),
                savedNotice.isPrivate(),
                savedNotice.getImageUrls()
        );
    }

    public void deleteNotice(Long id) {
        noticeRepository.deleteById(id);
    }

    public List<Notice> getAllNotices() {
        return noticeRepository.findAll();
    }

    public Page<Notice> getAllPublicNotices(Pageable pageable) {
        return noticeRepository.findByIsPrivateFalseOrderByDateDesc(pageable);
    }

    public Page<Notice> getNoticesByDateRange(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        return noticeRepository.findByIsPrivateFalseAndDateBetweenOrderByDateDesc(startDate, endDate, pageable);
    }

    public Page<Notice> getNoticesByCategory(String category, Pageable pageable) {
        return noticeRepository.findByIsPrivateFalseAndCategoryOrderByDateDesc(category, pageable);
    }

    public Page<Notice> getAllPrivateNotices(Pageable pageable) {
        return noticeRepository.findByIsPrivateTrueOrderByDateDesc(pageable);
    }
}