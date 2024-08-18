package com.example.dopamines.domain.board.community.free.repository;

import com.example.dopamines.domain.board.community.free.model.entity.FreePost;
import com.example.dopamines.domain.board.community.free.model.entity.FreePostLike;
import com.example.dopamines.domain.user.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface FreePostLikeRepository extends JpaRepository<FreePostLike,Long> {
    @Query("SELECT f FROM FreePostLike f WHERE f.user.idx = :userIdx and f.freePost.idx = :freePostIdx")
    Optional<FreePostLike> findByUserAndFreePost(Long userIdx, Long freePostIdx);
    boolean existsByUserAndFreePost(User user, FreePost freePost);

    @Query("SELECT CASE WHEN COUNT(f) > 0 THEN true ELSE false END FROM FreePostLike f WHERE f.user.idx = :userIdx AND f.freePost.idx = :freePostIdx")
    boolean existsByUserIdxAndFreePostIdx(Long userIdx, Long freePostIdx);
}
