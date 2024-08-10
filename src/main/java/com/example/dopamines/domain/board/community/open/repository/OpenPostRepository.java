package com.example.dopamines.domain.board.community.open.repository;

import com.example.dopamines.domain.board.community.open.model.entity.OpenPost;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface OpenPostRepository extends JpaRepository<OpenPost, Long> {
    @Query("SELECT f FROM OpenPost f")
    Optional<Slice<OpenPost>> findAllWithPaging(Pageable pageable);

    @Query("SELECT f FROM OpenPost f JOIN FETCH f.user u WHERE f.idx = :idx")
    Optional<OpenPost> findByIdWithAuthor(Long idx);

    //Optional<OpenPostImage> findBy

    @Query("SELECT f FROM OpenPost f WHERE LOCATE(:keyword, f.title) > 0 OR LOCATE(:keyword, f.content) > 0")
    Optional<Slice<OpenPost>> search(Pageable pageable, String keyword);
}
