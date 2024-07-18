package com.example.dopamines.domain.board.community.open.repository;

import com.example.dopamines.domain.board.community.open.model.entity.OpenBoard;
import com.example.dopamines.domain.board.community.open.model.entity.OpenLike;
import com.example.dopamines.domain.user.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface OpenLikeRepository extends JpaRepository<OpenLike,Long> {
    @Query("SELECT f FROM OpenLike f WHERE f.user.idx = :userIdx and f.openBoard.idx = :openBoardIdx")
    Optional<OpenLike> findByUserAndOpenBoard(Long userIdx, Long openBoardIdx);
    boolean existsByUserAndOpenBoard(User user, OpenBoard openBoard);
}
