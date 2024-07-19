package com.example.dopamines.domain.board.community.open.model.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OpenPostReq {
    private String title;
    private String content;
    private String image;
}
