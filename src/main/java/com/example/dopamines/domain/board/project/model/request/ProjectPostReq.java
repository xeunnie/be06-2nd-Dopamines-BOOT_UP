package com.example.dopamines.domain.board.project.model.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProjectPostReq {

    private String title;
    private String contents;
    private Integer courseNum;
    private Long teamIdx;
}
