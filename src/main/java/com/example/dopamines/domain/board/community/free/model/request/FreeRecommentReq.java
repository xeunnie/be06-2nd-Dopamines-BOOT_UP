package com.example.dopamines.domain.board.community.free.model.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FreeRecommentReq {
    private Long commentIdx; // freeCommentIdx
    private String content;
}
