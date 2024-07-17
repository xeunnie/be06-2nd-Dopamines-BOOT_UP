package com.example.dopamines.domain.admin.service;

import com.example.dopamines.domain.admin.model.request.AdminSignupRequest;
import com.example.dopamines.domain.admin.model.request.UserAssignedRequest;
import com.example.dopamines.domain.admin.model.request.UserBlackRequest;
import com.example.dopamines.domain.user.model.entity.User;
import com.example.dopamines.domain.user.repository.UserRepository;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    
    public void signupAdmin(AdminSignupRequest request) {
        System.out.println(request.getEmail());
        System.out.println(request.getPhoneNumber());
        System.out.println(request.getPassword());
        LocalDateTime localDateTime = LocalDateTime.now();
        localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        User user = User.builder()
                .email(request.getEmail())
                .password(bCryptPasswordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .nickname(request.getNickname())
                .phoneNumber(request.getPhoneNumber())
                .address("-")
                .role("ROLE_ADMIN")
                .createdAt(localDateTime)
                .updatedAt(localDateTime)
                .build();
        user.setActiveOn(true);
        userRepository.save(user);
    }

    // -------------- user 정보 관리 --------------------
    // 한화 수강생 승인 관리 (승인 버튼)
    public void setUserStatus(UserAssignedRequest request){
        Optional<User> result = userRepository.findById(request.getIdx());
        if(!result.isPresent()){
            User user = result.get();
            user.activeHanwhaUser(request.getCourseNum());  // TEMPORARY_USER -> USER 변경
            userRepository.save(user);
        }
    }
    // 회원 활동 정지 (벤 버튼)
    public void setBlackList(UserBlackRequest request){
        Optional<User> result = userRepository.findById(request.getIdx());
        if(!result.isPresent()){
            User user = result.get();
            user.setToBlackList();
            userRepository.save(user);
        }
    }
}
