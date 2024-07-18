package com.example.dopamines.domain.board.community.open.model.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OpenRecommentUpdateReq {
    private Long idx; // RecommentIdx
    private String content;
}
