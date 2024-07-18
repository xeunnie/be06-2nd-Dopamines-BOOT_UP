package com.example.dopamines.domain.board.community.open.model.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OpenRecommentReq {
    private Long commentIdx; // openCommentIdx
    private String content;
}
