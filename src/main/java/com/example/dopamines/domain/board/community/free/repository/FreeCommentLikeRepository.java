package com.example.dopamines.domain.board.community.free.repository;

import com.example.dopamines.domain.board.community.free.model.entity.FreeCommentLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface FreeCommentLikeRepository extends JpaRepository<FreeCommentLike,Long> {
    @Query("SELECT l FROM FreeCommentLike l WHERE l.user.idx =:userIdx and l.freeComment.idx =:freeCommentIdx")
    Optional<FreeCommentLike> findByUserAndIdx(Long userIdx, Long freeCommentIdx);
}
