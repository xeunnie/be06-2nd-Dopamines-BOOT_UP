package com.example.dopamines.domain.board.community.free.repository;


import com.example.dopamines.domain.board.community.free.model.entity.FreeBoard;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FreeBoardRepository extends JpaRepository<FreeBoard, Long> {
    @Query("SELECT f FROM FreeBoard f")
    public Slice<FreeBoard> findAllWithPaging(Pageable pageable);
}
