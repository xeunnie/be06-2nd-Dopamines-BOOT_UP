package com.example.dopamines.domain.board.notice.service;

import com.example.dopamines.domain.board.notice.model.entity.Notice;
import com.example.dopamines.domain.board.notice.model.request.NoticeRequestDto;
import com.example.dopamines.domain.board.notice.model.response.NoticeResponseDto;
import com.example.dopamines.domain.board.notice.repository.NoticeRepository;
import com.example.dopamines.global.common.BaseException;
import com.example.dopamines.global.common.BaseResponse;
import com.example.dopamines.global.common.BaseResponseStatus;
import com.example.dopamines.global.infra.s3.CloudFileUploadService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final CloudFileUploadService fileUploadService;

    // 공지사항 생성
    @Transactional
    public BaseResponse<NoticeResponseDto> saveNotice(NoticeRequestDto noticeRequestDto) {
        try {
            Notice notice = noticeRequestDto.toEntity();
            Notice savedNotice = noticeRepository.save(notice);
            return new BaseResponse<>(new NoticeResponseDto(savedNotice));
        } catch (Exception e) {
            return new BaseResponse<>(false, BaseResponseStatus.NOTICE_SAVE_FAILED.getMessage(), BaseResponseStatus.NOTICE_SAVE_FAILED.getCode(), null);
        }
    }

    // 공지사항 조회
    public NoticeResponseDto getNotice(Long id) {
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOTICE_NOT_FOUND));
        return new NoticeResponseDto(notice);
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


    // 공지사항 수정
    @Transactional
    public NoticeResponseDto updateNotice(Long id, NoticeRequestDto noticeDetails) {
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOTICE_NOT_FOUND));
        updateNoticeDetails(notice, noticeDetails);
        Notice savedNotice = noticeRepository.save(notice);
        return convertToNoticeResponseDto(savedNotice);
    }

    private NoticeResponseDto convertToNoticeResponseDto(Notice notice) {
        return new NoticeResponseDto(
                notice.getId(),
                notice.getTitle(),
                notice.getContent(),
                notice.getDate(),
                notice.getCategory(),
                notice.isPrivate(),
                notice.getImageUrls());
    }

    private NoticeResponseDto updateNoticeDetails(Notice notice, NoticeRequestDto noticeDetails) {
        notice.setTitle(noticeDetails.getTitle());
        notice.setContent(noticeDetails.getContent());
        notice.setCategory(noticeDetails.getCategory());
        notice.setPrivate(noticeDetails.isPrivate());
        Notice savedNotice = noticeRepository.save(notice);
        return new NoticeResponseDto(savedNotice);
    }


    // 공지사항 삭제
    public void deleteNotice(Long id) {
        try {
            noticeRepository.deleteById(id);
        } catch (EntityNotFoundException e) {
            throw new BaseException(BaseResponseStatus.NOTICE_NOT_FOUND);
        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.NOTICE_DELETE_FAILED);
        }
    }

}