package com.example.dopamines.domain.board.community.free.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FreePostRes {
    private Long idx;
    private String title;
    private String content;

}
