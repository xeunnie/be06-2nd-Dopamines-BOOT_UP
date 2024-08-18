package com.example.dopamines.domain.board.community.free.repository;

import com.example.dopamines.domain.board.community.free.model.entity.FreeCommentLike;
import com.example.dopamines.domain.board.community.free.model.entity.FreeRecommentLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface FreeRecommentLikeRepository extends JpaRepository<FreeRecommentLike,Long> {
    @Query("SELECT l FROM FreeRecommentLike l WHERE l.user.idx =:userIdx and l.freeRecomment.idx =:freeRecommentIdx")
    Optional<FreeRecommentLike> findByUserAndIdx(Long userIdx, Long freeRecommentIdx);

    @Query("SELECT CASE WHEN COUNT(f) > 0 THEN true ELSE false END FROM FreeRecommentLike f WHERE f.user.idx = :userIdx AND f.freeRecomment.idx = :freeRecommentIdx")
    boolean existsByUserIdxAndFreeRecommentIdx(Long userIdx, Long freeRecommentIdx);
}
