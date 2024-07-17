package com.example.dopamines.domain.user.controller;

import com.example.dopamines.domain.user.model.request.UserSignupRequest;
import com.example.dopamines.domain.user.model.response.UserSignupResponse;
import com.example.dopamines.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserSignupResponse> singup(@RequestBody UserSignupRequest request){
        UserSignupResponse signupResult = userService.signup(request);
        return ResponseEntity.ok(signupResult);
    }

}
