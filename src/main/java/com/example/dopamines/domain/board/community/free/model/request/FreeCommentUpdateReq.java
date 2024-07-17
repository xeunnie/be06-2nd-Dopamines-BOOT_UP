package com.example.dopamines.domain.board.community.free.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FreeCommentUpdateReq {
    private Long idx; // commentIdx
    private String content;
}
