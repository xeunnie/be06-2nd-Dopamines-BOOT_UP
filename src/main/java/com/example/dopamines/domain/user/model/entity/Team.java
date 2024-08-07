package com.example.dopamines.domain.user.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    private String teamName;

    private Integer courseNum;

    @OneToMany(mappedBy = "team")
    private List<User> students = new ArrayList<User>();

    @Builder
    public Team(Long idx, String teamName, List<User> students, Integer courseNum) {
        this.idx = idx;
        this.teamName = teamName;
        this.students = students;
        this.courseNum = courseNum;
    }

    public void setStudents(List<User> students) {
        this.students = students;
    }
}
