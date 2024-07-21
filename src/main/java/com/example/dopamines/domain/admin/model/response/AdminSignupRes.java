package com.example.dopamines.domain.admin.model.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AdminSignupRes {
    private String email;
    private String password;
    private String name;
    private String nickname;
    private String phoneNumber;
}
