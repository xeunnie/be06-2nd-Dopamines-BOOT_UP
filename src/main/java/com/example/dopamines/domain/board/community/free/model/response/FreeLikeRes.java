package com.example.dopamines.domain.board.community.free.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FreeLikeRes {
    private Long idx;
    private Long boardIdx;
    private Long userIdx;
}
