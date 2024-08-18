package com.example.dopamines.domain.board.community.free.repository;

import com.example.dopamines.domain.board.community.free.model.entity.FreeCommentLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface FreeCommentLikeRepository extends JpaRepository<FreeCommentLike,Long> {
    @Query("SELECT l FROM FreeCommentLike l WHERE l.user.idx =:userIdx and l.freeComment.idx =:freeCommentIdx")
    Optional<FreeCommentLike> findByUserAndIdx(Long userIdx, Long freeCommentIdx);

    @Query("SELECT CASE WHEN COUNT(f) > 0 THEN true ELSE false END FROM FreeCommentLike f WHERE f.user.idx = :userIdx AND f.freeComment.idx = :freeCommentIdx")
    boolean existsByUserIdxAndFreeCommentIdx(Long userIdx, Long freeCommentIdx);
}
