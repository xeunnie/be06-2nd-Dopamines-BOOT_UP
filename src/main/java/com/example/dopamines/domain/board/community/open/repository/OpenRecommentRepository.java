package com.example.dopamines.domain.board.community.open.repository;

import com.example.dopamines.domain.board.community.open.model.entity.OpenRecomment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OpenRecommentRepository extends JpaRepository<OpenRecomment,Long> {
    @Query("SELECT fr FROM OpenRecomment fr JOIN FETCH fr.user u WHERE fr.openComment.idx = :commentIdx")
        //개선 전: @Query("SELECT fr FROM OpenRecomment fr  WHERE fr.openComment.idx = :commentIdx")
    Slice<OpenRecomment> findAllWithPaging(Pageable pageable, Long commentIdx);
}
