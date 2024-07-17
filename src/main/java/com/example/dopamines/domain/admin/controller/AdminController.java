package com.example.dopamines.domain.admin.controller;

import com.example.dopamines.domain.admin.model.request.AdminSignupRequest;
import com.example.dopamines.domain.admin.model.request.UserAssignedRequest;
import com.example.dopamines.domain.admin.model.request.UserBlackRequest;
import com.example.dopamines.domain.admin.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/signup")
    public void signupAdmin(@RequestBody AdminSignupRequest request) {
        adminService.signupAdmin(request);
    }

    @PostMapping("/assign")
    public void assignedUser(@RequestBody UserAssignedRequest request){
        adminService.setUserStatus(request);
    }

    @PostMapping("/user/black")
    public void assignedUser(@RequestBody UserBlackRequest request){
        adminService.setBlackList(request);
    }
}