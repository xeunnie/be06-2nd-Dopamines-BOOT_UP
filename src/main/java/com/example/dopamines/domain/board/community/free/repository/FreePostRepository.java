package com.example.dopamines.domain.board.community.free.repository;


import com.example.dopamines.domain.board.community.free.model.entity.FreePost;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FreePostRepository extends JpaRepository<FreePost, Long> {
    @Query("SELECT f FROM FreePost f")
    Slice<FreePost> findAllWithPaging(Pageable pageable);

    @Query("SELECT f FROM FreePost f WHERE LOCATE(:keyword, f.title) > 0 OR LOCATE(:keyword, f.content) > 0")
    Slice<FreePost> search(Pageable pageable, String keyword);

}
