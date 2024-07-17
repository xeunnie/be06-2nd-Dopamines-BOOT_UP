package com.example.dopamines.domain.board.community.free.repository;

import com.example.dopamines.domain.board.community.free.model.entity.FreeBoard;
import com.example.dopamines.domain.board.community.free.model.entity.FreeLike;
import com.example.dopamines.domain.user.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface FreeLikeRepository extends JpaRepository<FreeLike,Long> {
    @Query("SELECT f FROM FreeLike f WHERE f.user.idx = :userIdx and f.freeBoard.idx = :freeBoardIdx")
    Optional<FreeLike> findByUserAndFreeBoard(Long userIdx, Long freeBoardIdx);
    boolean existsByUserAndFreeBoard(User user, FreeBoard freeBoard);
}
