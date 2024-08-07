package com.example.dopamines.domain.board.project.model.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProjectPostUpdateReq {
    private Long idx;

    private String title;
    private String contents;
    private Integer courseNum;
    private Long teamIdx;
    private String gitUrl;
    private String sourceUrl;
}
