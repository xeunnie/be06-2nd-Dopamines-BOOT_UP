package com.example.dopamines.domain.board.community.free.model.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FreePostUpdateReq {
    private Long idx;
    private String title;
    private String content;
}
