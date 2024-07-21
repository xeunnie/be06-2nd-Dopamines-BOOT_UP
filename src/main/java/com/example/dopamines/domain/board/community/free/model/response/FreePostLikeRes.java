package com.example.dopamines.domain.board.community.free.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FreePostLikeRes {
    // TODO :  사용할지 안할지 결정
    private Long idx;
    private Long postIdx;
    private Long userIdx;
}
