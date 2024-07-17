package com.example.dopamines.domain.board.market.repository;

import com.example.dopamines.domain.board.market.model.entity.MarketPost;
import com.example.dopamines.domain.board.market.repository.querydsl.MarketPostRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarketPostRepository extends JpaRepository<MarketPost, Long> , MarketPostRepositoryCustom {
}

