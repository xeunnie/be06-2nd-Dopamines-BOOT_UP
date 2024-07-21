package com.example.dopamines.domain.board.notice.repository;

import com.example.dopamines.domain.board.notice.model.entity.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
    Optional<Page<Notice>> findByIsPrivateFalseOrderByDateDesc(Pageable pageable);
    Optional<Page<Notice>> findByIsPrivateFalseAndDateBetweenOrderByDateDesc(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
    Optional<Page<Notice>> findByIsPrivateFalseAndCategoryOrderByDateDesc(String category, Pageable pageable);
    Optional<Page<Notice>> findByIsPrivateTrueOrderByDateDesc(Pageable pageable);

}