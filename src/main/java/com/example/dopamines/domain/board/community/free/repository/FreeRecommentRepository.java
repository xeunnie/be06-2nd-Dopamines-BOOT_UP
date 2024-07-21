package com.example.dopamines.domain.board.community.free.repository;

import com.example.dopamines.domain.board.community.free.model.entity.FreeRecomment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FreeRecommentRepository extends JpaRepository<FreeRecomment,Long> {
    @Query("SELECT fr FROM FreeRecomment fr JOIN FETCH fr.user u WHERE fr.freeComment.idx = :commentIdx")
    //개선 전: @Query("SELECT fr FROM FreeRecomment fr  WHERE fr.freeComment.idx = :commentIdx")
    Slice<FreeRecomment> findAllWithPaging(Pageable pageable, Long commentIdx);
}
