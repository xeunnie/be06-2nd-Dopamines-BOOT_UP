package com.example.dopamines.domain.board.notice.repository;

import com.example.dopamines.domain.board.notice.model.entity.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
    Page<Notice> findByIsPrivateFalseOrderByDateDesc(Pageable pageable);
    Page<Notice> findByIsPrivateFalseAndDateBetweenOrderByDateDesc(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
    Page<Notice> findByIsPrivateFalseAndCategoryOrderByDateDesc(String category, Pageable pageable);
    Page<Notice> findByIsPrivateTrueOrderByDateDesc(Pageable pageable);

}