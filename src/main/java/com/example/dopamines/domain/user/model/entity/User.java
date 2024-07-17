package com.example.dopamines.domain.user.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

    // Todo : 자동생성으로 바꾸기
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    @Builder
    public User(String email, String password, String name, String nickname, String role,
                String phoneNumber, String address,
                LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.role = role;
        this.enabled = false;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.socialLogined = false;
//        this.status = status;     //사용 의도 미정
//        this.courseNum = courseNum; // 관리자 등록 미정
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // 이메일 검증
    public void setActiveOn(boolean enabled) {
        this.enabled = enabled;
    }


}
