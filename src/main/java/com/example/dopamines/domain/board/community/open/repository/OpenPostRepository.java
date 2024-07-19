package com.example.dopamines.domain.board.community.open.repository;


import com.example.dopamines.domain.board.community.open.model.entity.OpenPost;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OpenPostRepository extends JpaRepository<OpenPost, Long> {
    @Query("SELECT f FROM OpenPost f")
    public Slice<OpenPost> findAllWithPaging(Pageable pageable);

    @Query("SELECT p FROM OpenPost p WHERE LOCATE(:keyword, p.title) > 0 OR LOCATE(:keyword, p.content) > 0")
    Slice<OpenPost> search(Pageable pageable, String keyword);
}
