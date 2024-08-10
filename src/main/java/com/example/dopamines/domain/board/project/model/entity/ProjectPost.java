package com.example.dopamines.domain.board.project.model.entity;

import com.example.dopamines.domain.user.model.entity.Team;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    private String title;
    @Column(name = "content", length = 65535) // length 속성을 사용하여 길이를 늘림
    private String contents;
    private Integer courseNum;
    private String gitUrl;
    private String sourceUrl;

    @OneToOne
    @JoinColumn(name = "team_idx")
    private Team team;
}