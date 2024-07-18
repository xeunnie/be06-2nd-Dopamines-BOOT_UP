package com.example.dopamines.domain.board.community.open.repository;

import com.example.dopamines.domain.board.community.open.model.entity.OpenCommentLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface OpenCommentLikeRepository extends JpaRepository<OpenCommentLike,Long> {
    @Query("SELECT l FROM OpenCommentLike l WHERE l.user.idx =:userIdx and l.openComment.idx =:openCommentIdx")
    Optional<OpenCommentLike> findByUserAndIdx(Long userIdx, Long openCommentIdx);
}
