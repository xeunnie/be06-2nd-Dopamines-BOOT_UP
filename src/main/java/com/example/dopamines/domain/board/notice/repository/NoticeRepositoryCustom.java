package com.example.dopamines.domain.board.notice.repository;


import com.example.dopamines.domain.board.notice.model.entity.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NoticeRepositoryCustom {
    Page<Notice> findNoticesByCriteria(Boolean isPrivate, String category, int page, int size);

    Page<Notice> findNotices();
}
