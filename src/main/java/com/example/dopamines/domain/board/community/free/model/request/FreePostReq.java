package com.example.dopamines.domain.board.community.free.model.request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FreePostReq {
    private String title;
    private String content;
    private List<String> images;
}
