package com.example.dopamines.domain.user.model.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter

public class UserSignupReq {

    @NotBlank
    @Email(message = "이메일 형식에 맞지 않습니다.")
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    @Size(min = 2, max = 8, message = "이름을 2~8자 사이로 입력해주세요.")
    private String name;
    @NotBlank
    @Size(min = 2, max = 8, message = "닉네임을 2~8자 사이로 입력해주세요.")
    private String nickname;
    @NotBlank
    private String phoneNumber;
    @NotBlank
    private String address;

}
