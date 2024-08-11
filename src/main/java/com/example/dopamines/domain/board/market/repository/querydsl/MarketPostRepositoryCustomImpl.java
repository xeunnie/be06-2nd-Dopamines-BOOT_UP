package com.example.dopamines.domain.board.market.repository.querydsl;

import com.example.dopamines.domain.board.market.model.entity.MarketPost;
import com.example.dopamines.domain.board.market.model.entity.QMarketPost;

import com.example.dopamines.domain.board.market.model.entity.QMarketProductImage;
import com.example.dopamines.domain.user.model.entity.QUser;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class MarketPostRepositoryCustomImpl implements MarketPostRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    private QMarketPost marketPost;
    private QUser user;
    private QMarketProductImage marketProductImage;

    public MarketPostRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
        this.marketPost = QMarketPost.marketPost;
        this.user = QUser.user;
        this.marketProductImage = QMarketProductImage.marketProductImage;
    }

    @Override
    public Slice<MarketPost> findAllWithPaging(Pageable pageable) {

        List<MarketPost> result = queryFactory
                .selectFrom(marketPost)
                .leftJoin(marketPost.user, user).fetchJoin()
                .orderBy(marketPost.idx.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize()+1)
                .fetch();

        Boolean hasnext = false;

        if(result.size() == pageable.getPageSize()+1) {
            hasnext = true;
        }

        return new SliceImpl<>(result, pageable, hasnext);
    }

    @Override
    public Slice<MarketPost> findAllInMarked(List<Long> postIds, Pageable pageable) {
        List<MarketPost> result = queryFactory
                .selectFrom(marketPost)
                .leftJoin(marketPost.user, user).fetchJoin()
                .where(marketPost.idx.in(postIds))
                .orderBy(marketPost.idx.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize()+1)
                .fetch();

        Boolean hasnext = false;

        if(result.size() == pageable.getPageSize()+1) {
            hasnext = true;
        }

        return new SliceImpl<>(result, pageable, hasnext);
    }


    @Override
    public MarketPost findByIdWithImages(Long idx) {
        MarketPost post = queryFactory
                .selectFrom(marketPost)
                .leftJoin(marketPost.images, marketProductImage).fetchJoin()
                .leftJoin(marketPost.user, user).fetchJoin()
                .where(marketPost.idx.eq(idx))
                .fetchOne();

        return post;
    }

    @Override
    public Slice<MarketPost> search(Pageable pageable, String keyword, Integer minPrice, Integer maxPrice) {
        List<MarketPost> result = queryFactory
                .selectFrom(marketPost)
                .leftJoin(marketPost.user, user).fetchJoin()
                .where(titleOrContentContains(keyword),greaterThanMinPrice(minPrice),lowerThanMaxPrice(maxPrice))
                .orderBy(marketPost.idx.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize()+1)
                .fetch();

        Boolean hasnext = false;

        if(result.size() == pageable.getPageSize()+1) {
            hasnext = true;
        }

        return new SliceImpl<>(result, pageable, hasnext);
    }


    private BooleanExpression titleOrContentContains(String keyword) {

        if (keyword == null || keyword.isBlank()) {
            log.info("[MARKET][SEARCH KEYWORD EMPTY] => return empty set");
            return marketPost.isNull();
        }

        return marketPost.title.containsIgnoreCase(keyword)
                .or(marketPost.content.containsIgnoreCase(keyword));

    }

    private BooleanExpression greaterThanMinPrice(Integer minPrice) {
        if (minPrice == null) {
            return null;
        }
        return marketPost.price.goe(minPrice);
    }

    private BooleanExpression lowerThanMaxPrice(Integer maxPrice) {
        if (maxPrice == null) {
            return null;
        }
        return marketPost.price.loe(maxPrice);
    }
}
