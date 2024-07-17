package com.example.dopamines.domain.user.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class UserEmailVerify {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String uuid;

    @Builder
    public UserEmailVerify(Long id, String email, String uuid) {
        this.id = id;
        this.email = email;
        this.uuid = uuid;
    }
}
