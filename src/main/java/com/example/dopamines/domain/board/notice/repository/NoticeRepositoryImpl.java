package com.example.dopamines.domain.board.notice.repository;

import com.example.dopamines.domain.board.notice.model.entity.Notice;
import com.example.dopamines.domain.board.notice.model.entity.QNotice;
import com.querydsl.core.types.dsl.BooleanExpression;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import lombok.RequiredArgsConstructor;
import com.querydsl.jpa.impl.JPAQueryFactory;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class NoticeRepositoryImpl implements NoticeRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<Notice> findNoticesByCriteria(Boolean isPrivate, String category, int page, int size) {
        QNotice notice = QNotice.notice; // Defined QNotice variable

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
    public Page<Notice> findNoticesByTitleAndContent(String title, String content, Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Notice> cq = cb.createQuery(Notice.class);
        Root<Notice> noticeRoot = cq.from(Notice.class);

        List<Predicate> predicates = new ArrayList<>();
        if (title != null && !title.isEmpty()) {
            predicates.add(cb.like(noticeRoot.get("title"), "%" + title + "%"));
        }
        if (content != null && !content.isEmpty()) {
            predicates.add(cb.like(noticeRoot.get("content"), "%" + content + "%"));
        }

        cq.where(cb.and(predicates.toArray(new Predicate[0])));
        TypedQuery<Notice> query = entityManager.createQuery(cq.select(noticeRoot));

        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<Notice> result = query.getResultList();
        return new PageImpl<>(result, pageable, result.size());
    }
}