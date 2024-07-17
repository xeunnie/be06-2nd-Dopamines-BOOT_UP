package com.example.dopamines.domain.board.notice.repository;

import com.example.dopamines.domain.board.notice.model.entity.Notice;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.dopamines.domain.board.notice.model.entity.QNotice.notice;

@Repository
@RequiredArgsConstructor
public class NoticeRepositoryImpl implements NoticeRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Notice> findNotices() {
        List<Notice> notices = queryFactory.selectFrom(notice).fetch();
        return null;
    }
}
