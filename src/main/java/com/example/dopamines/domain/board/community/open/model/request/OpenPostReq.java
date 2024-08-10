package com.example.dopamines.domain.board.community.open.model.request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OpenPostReq {
    private String title;
    private String content;
    private List<String> images;
}
