package com.example.dopamines.domain.user.model.request;


import lombok.Getter;

@Getter
public class UserSignupRequest {

    private String email;
    private String password;
    private String name;
    private String nickname;
    private String phoneNumber;
    private String address;

}
