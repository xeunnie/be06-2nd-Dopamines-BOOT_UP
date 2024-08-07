package com.example.dopamines.domain.board.project.model.request;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ProjectPostReq {

    private String title;
    private String contents;
    private Integer courseNum;
    private String gitUrl;
    private Long teamIdx;
    private List<String> images;
}
