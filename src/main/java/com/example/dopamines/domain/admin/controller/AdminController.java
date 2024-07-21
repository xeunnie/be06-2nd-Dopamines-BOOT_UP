package com.example.dopamines.domain.admin.controller;

import com.example.dopamines.domain.admin.model.request.AdminSignupReq;
import com.example.dopamines.domain.admin.model.request.UserAssignedReq;
import com.example.dopamines.domain.admin.model.request.UserBlackReq;
import com.example.dopamines.domain.admin.model.response.AdminSignupRes;
import com.example.dopamines.domain.admin.model.response.UserAssignedRes;
import com.example.dopamines.domain.admin.model.response.UserBlackRes;
import com.example.dopamines.domain.admin.service.AdminService;
import com.example.dopamines.global.common.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<BaseResponse<?>> signupAdmin(@RequestBody AdminSignupReq request) {
        AdminSignupRes result = adminService.signupAdmin(request);
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(result));
    }

    @PostMapping("/assign")
    public ResponseEntity<BaseResponse<?>> assignedUser(@RequestBody UserAssignedReq request){
        UserAssignedRes result = adminService.setUserStatus(request);
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(result));
    }

    @PostMapping("/user/black")
    public ResponseEntity<BaseResponse<?>> assignedUser(@RequestBody UserBlackReq request){
        UserBlackRes result = adminService.setBlackList(request);
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(result));
    }
}