package com.example.dopamines.domain.board.community.open.model.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class OpenCommentReadRes {
    private Long idx;
    private Long openPostIdx;
    private String content;
    private String author;
    private LocalDateTime createdAt;
    private Integer likeCount;
    private List<OpenRecommentReadRes> recommentList;

}
