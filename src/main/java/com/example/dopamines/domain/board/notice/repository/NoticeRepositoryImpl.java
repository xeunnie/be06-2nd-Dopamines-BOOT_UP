package com.example.dopamines.domain.board.notice.repository;

import com.example.dopamines.domain.board.notice.model.entity.Notice;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.example.dopamines.domain.board.notice.model.entity.QNotice.notice;

@Repository
@RequiredArgsConstructor
public class NoticeRepositoryImpl implements NoticeRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Notice> findNoticesByCriteria(Boolean isPrivate, String category, int page, int size) {
        BooleanExpression predicate = notice.isNotNull();

        if (isPrivate != null) {
            predicate = predicate.and(notice.isPrivate.eq(isPrivate));
        }
        if (category != null && !category.isEmpty()) {
            predicate = predicate.and(notice.category.eq(category));
        }

        List<Notice> notices = queryFactory
                .selectFrom(notice)
                .where(predicate)
                .offset(page * size)
                .limit(size)
                .fetch();

        long total = queryFactory
                .selectFrom(notice)
                .where(predicate)
                .fetchCount();

        return new PageImpl<>(notices, PageRequest.of(page, size), total);
    }

    @Override
    public Page<Notice> findNotices() {
        return null;
    }
}