package com.example.dopamines.domain.board.community.free.repository;

import com.example.dopamines.domain.board.community.free.model.entity.FreeComment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FreeCommentRepository extends JpaRepository<FreeComment,Long> {
    //@Query("SELECT fc FROM FreeComment fc JOIN FETCH fc.user u JOIN FETCH fc.likes l WHERE fc.freePost.idx = :postIdx")
    @Query("SELECT fc FROM FreeComment fc  WHERE fc.freePost.idx = :postIdx")
    Slice<FreeComment> findAllWithPaging(Pageable pageable, Long postIdx); // 페이지 사이즈가 10으로 고정


}
