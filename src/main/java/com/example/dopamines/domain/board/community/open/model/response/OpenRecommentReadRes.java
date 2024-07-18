package com.example.dopamines.domain.board.community.open.model.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class OpenRecommentReadRes {
    private Long idx;
    private Long openBoardIdx;
    private Long commentIdx;
    private String content;
    private String author;
    private LocalDateTime createdAt;
    private Integer likeCount;
}
