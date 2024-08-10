package com.example.dopamines.domain.board.community.open.repository;

import com.example.dopamines.domain.board.community.open.model.entity.OpenComment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OpenCommentRepository extends JpaRepository<OpenComment,Long> {
    @Query("SELECT fc FROM OpenComment fc JOIN FETCH fc.user u WHERE fc.openPost.idx = :postIdx")
        //@Query("SELECT fc FROM OpenComment fc  WHERE fc.openPost.idx = :postIdx")
    Slice<OpenComment> findAllWithPaging(Pageable pageable, Long postIdx); // 페이지 사이즈가 10으로 고정

}
