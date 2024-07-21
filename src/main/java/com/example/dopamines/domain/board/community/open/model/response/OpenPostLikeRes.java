package com.example.dopamines.domain.board.community.open.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OpenPostLikeRes {
    // TODO :  사용할지 안할지 결정
    private Long idx;
    private Long postIdx;
    private Long userIdx;
}
