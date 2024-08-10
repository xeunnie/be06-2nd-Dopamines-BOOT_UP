package com.example.dopamines.domain.board.community.open.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class OpenPostRes {
    private Long idx;
    private String title;
    private String content;

    private String nickName;
    private LocalDateTime createdAt;
}
