package com.example.dopamines.domain.board.community.open.repository;

import com.example.dopamines.domain.board.community.open.model.entity.OpenPost;
import com.example.dopamines.domain.board.community.open.model.entity.OpenPostLike;
import com.example.dopamines.domain.user.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface OpenPostLikeRepository extends JpaRepository<OpenPostLike,Long> {
    @Query("SELECT f FROM OpenPostLike f WHERE f.user.idx = :userIdx and f.openPost.idx = :openPostIdx")
    Optional<OpenPostLike> findByUserAndOpenPost(Long userIdx, Long openPostIdx);
    boolean existsByUserAndOpenPost(User user, OpenPost openPost);
}
