package com.example.dopamines.domain.user.model.request;

import lombok.Getter;

@Getter
public class UserLoginReq {
    private String email;
    private String password;
}
