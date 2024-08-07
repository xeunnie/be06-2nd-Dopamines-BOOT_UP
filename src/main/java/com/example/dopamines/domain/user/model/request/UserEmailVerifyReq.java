package com.example.dopamines.domain.user.model.request;

import lombok.Getter;

@Getter
public class UserEmailVerifyReq {
    private String email;
    private String uuid;
}
