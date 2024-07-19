package com.example.dopamines.domain.board.community.open.model.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class OpenPostReadRes {
    private Long idx;
    private String title;
    private String content;

    //Todo : 작성자의 이름만 주면 될까? User에 대해서 더 필요한 건 없을까?
    // -> 익명이라고 했으니까
    private String author; // user_nickname
    private String image;
    private LocalDateTime created_at;
    private int likeCount;

    private List<OpenCommentReadRes> openCommentList;
}
