package com.example.dopamines.domain.board.community.free.model.response;

import com.example.dopamines.domain.board.community.free.model.entity.FreeComment;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Slice;

@Getter
@Builder
public class FreePostReadRes {
    private Long idx;
    private String title;
    private String content;

    //Todo : 작성자의 이름만 주면 될까? User에 대해서 더 필요한 건 없을까?
    // -> 익명이라고 했으니까
    private String author; // user_nickname
    private List<String> imageUrlList;
    private LocalDateTime created_at;
    private int likeCount;

    private List<FreeCommentReadRes> freeCommentList;
}
