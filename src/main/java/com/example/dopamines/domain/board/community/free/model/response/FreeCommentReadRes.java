package com.example.dopamines.domain.board.community.free.model.response;

import com.example.dopamines.domain.board.community.free.model.entity.FreeRecomment;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class FreeCommentReadRes {
    private Long idx;
    private Long freePostIdx;
    private String content;
    private String author;
    private LocalDateTime createdAt;
    private Integer likeCount;
    private List<FreeRecommentReadRes> recommentList;
}
