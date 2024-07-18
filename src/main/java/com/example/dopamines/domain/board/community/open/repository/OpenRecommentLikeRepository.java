package com.example.dopamines.domain.board.community.open.repository;

import com.example.dopamines.domain.board.community.open.model.entity.OpenRecommentLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface OpenRecommentLikeRepository extends JpaRepository<OpenRecommentLike,Long> {
    @Query("SELECT l FROM OpenRecommentLike l WHERE l.user.idx =:userIdx and l.openRecomment.idx =:openRecommentIdx")
    Optional<OpenRecommentLike> findByUserAndIdx(Long userIdx, Long openRecommentIdx);
}
