package com.example.dopamines.domain.board.community.open.repository;


import com.example.dopamines.domain.board.community.open.model.entity.OpenBoard;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OpenBoardRepository extends JpaRepository<OpenBoard, Long> {
    @Query("SELECT f FROM OpenBoard f")
    public Slice<OpenBoard> findAllWithPaging(Pageable pageable);
}
