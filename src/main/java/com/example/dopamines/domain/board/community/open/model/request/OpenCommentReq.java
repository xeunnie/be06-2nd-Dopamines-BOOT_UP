package com.example.dopamines.domain.board.community.open.model.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OpenCommentReq {
    private Long idx; // openBoardIdx
    private String content;
}
