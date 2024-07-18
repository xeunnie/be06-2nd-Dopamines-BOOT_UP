package com.example.dopamines.domain.board.community.free.model.request;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class FreeRecommentUpdateReq {
    private Long idx; // RecommentIdx
    private String content;
}
