package com.example.dopamines.domain.board.project.model.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProjectPostTeamListRes {
    private Long idx;
    private String teamName;
}