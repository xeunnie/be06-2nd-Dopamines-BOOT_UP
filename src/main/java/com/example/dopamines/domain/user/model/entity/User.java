package com.example.dopamines.domain.user.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    @NotNull
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String name;

    @NotNull
    private String nickname;

    @NotNull
    private String role;

    @NotNull
    private boolean enabled;

    @NotNull
    private String phoneNumber;

    @NotNull
    private String address;

    @NotNull
    private boolean socialLogined;

    private boolean status;

    private Integer courseNum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_idx")
    private Team team;

    // Todo : 자동생성으로 바꾸기
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public User(Long idx, String email, String password, String name, String nickname, String role,
                String phoneNumber, String address,
                LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.idx = idx;
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.role = role;
        this.enabled = false;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.socialLogined = false;
        this.status = true;
        this.courseNum = null;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    //Todo : Q)자체 로직으로 ADMIN 계정인지 확인할 지 고민
    public void activeHanwhaUser(Integer courseNum){
        this.status = true;
        this.courseNum = courseNum;
        this.role = "ROLE_USER";
    }

    //Todo : Q)자체 로직으로 ADMIN 계정인지 확인할 지 고민
    public void setToBlackList(){
        this.status = false;
    }

    // 이메일 검증
    public void setActiveOn(boolean enabled) {
        this.enabled = enabled;
    }

    public void setCourseNum(Integer courseNum) {
        this.courseNum = courseNum;
    }

    public void setSocialLogined(boolean socialLogined) {
        this.socialLogined = socialLogined;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
