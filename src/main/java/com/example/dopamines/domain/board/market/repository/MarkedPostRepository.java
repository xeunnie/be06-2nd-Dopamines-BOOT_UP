package com.example.dopamines.domain.board.market.repository;

import com.example.dopamines.domain.board.market.model.entity.MarkedPost;
import com.example.dopamines.domain.board.market.model.entity.MarketPost;
import com.example.dopamines.domain.user.model.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MarkedPostRepository extends JpaRepository<MarkedPost, Long> {
    @Query("SELECT m FROM MarkedPost m WHERE m.user.idx = :userIdx and m.marketPost.idx = :postIdx")
    MarkedPost findByUserAndMarketPost(Long userIdx, Long postIdx);
    boolean existsByUserAndMarketPost(User user, MarketPost post);

    @Query("SELECT m.marketPost.idx FROM MarkedPost m WHERE m.user.idx = :userIdx")
    List<Long> findPostIdsByUserId(Long userIdx);
}
