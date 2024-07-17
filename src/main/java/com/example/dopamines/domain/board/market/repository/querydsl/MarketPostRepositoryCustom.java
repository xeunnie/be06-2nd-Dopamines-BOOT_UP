package com.example.dopamines.domain.board.market.repository.querydsl;

import com.example.dopamines.domain.board.market.model.entity.MarketPost;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface MarketPostRepositoryCustom {
    Slice<MarketPost> search(Pageable pageable, String keyword, Integer minPrice, Integer maxPrice);
    Optional<MarketPost> findByIdWithImages(Long idx);
    Slice<MarketPost> findAllWithPaging(Pageable pageable);

    Slice<MarketPost> findAllInMarked(List<Long> postIds, Pageable pageable);
}
