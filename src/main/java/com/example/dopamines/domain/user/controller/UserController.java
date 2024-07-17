package com.example.dopamines.domain.user.controller;

import com.example.dopamines.domain.user.model.request.UserSignupRequest;
import com.example.dopamines.domain.user.model.response.UserSignupResponse;
import com.example.dopamines.domain.user.service.UserEmailService;
import com.example.dopamines.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final UserEmailService emailService;

    @PostMapping("/signup")
    public ResponseEntity<UserSignupResponse> singup(@RequestBody UserSignupRequest request){

        UserSignupResponse signupResult = userService.signup(request);

        if(signupResult == null){
            return ResponseEntity.badRequest().body(null);
        }
        // 인증을 할 uuid를 생성하고 일단 저장
        String getUuid = emailService.sendEmail(request);
        emailService.save(request,getUuid);

        return ResponseEntity.ok(signupResult);
    }

    @GetMapping("/active")
    public ResponseEntity<String> setActiveUser(String email, String uuid){
        //요청 이메일 및 uuid와 서버 uuid 비교
        boolean successVerifying = emailService.verifyUser(email, uuid);
        if(successVerifying){
            userService.setActiveOn(email);
            return ResponseEntity.ok("계정 활성화 성공");
        }
        return ResponseEntity.badRequest().body("계정 활성화 실패");
    }

    @GetMapping("/test")
    public void test(){
        userService.insertUserAndTeam();
    }
}
