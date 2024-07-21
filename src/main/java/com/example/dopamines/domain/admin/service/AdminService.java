package com.example.dopamines.domain.admin.service;

import com.example.dopamines.domain.admin.model.request.AdminSignupReq;
import com.example.dopamines.domain.admin.model.request.UserAssignedReq;
import com.example.dopamines.domain.admin.model.request.UserBlackReq;
import com.example.dopamines.domain.admin.model.response.AdminSignupRes;
import com.example.dopamines.domain.admin.model.response.UserAssignedRes;
import com.example.dopamines.domain.admin.model.response.UserBlackRes;
import com.example.dopamines.domain.user.model.entity.User;
import com.example.dopamines.domain.user.repository.UserRepository;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.example.dopamines.global.common.BaseException;
import com.example.dopamines.global.common.BaseResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    
    public AdminSignupRes signupAdmin(AdminSignupReq request) {
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
        user = userRepository.save(user);

        return AdminSignupRes.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .name(user.getName())
                .nickname(user.getNickname())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }

    // -------------- user 정보 관리 --------------------
    // 한화 수강생 승인 관리 (승인 버튼)
    public UserAssignedRes setUserStatus(UserAssignedReq request){
        User result = userRepository.findById(request.getIdx())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.ADMIN_NOT_FOUND));
        if(result != null){
            result.activeHanwhaUser(request.getCourseNum());  // TEMPORARY_USER -> USER 변경
            User user = userRepository.save(result);

            return UserAssignedRes.builder()
                    .courseNum(user.getCourseNum())
                    .name(user.getName())
                    .status(user.isStatus())
                    .role(user.getRole())
                    .build();
        }

        return null;
    }
    // 회원 활동 정지 (벤 버튼)
    public UserBlackRes setBlackList(UserBlackReq request){
        User result = userRepository.findById(request.getIdx())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.ADMIN_NOT_FOUND));
        if(result != null){
            result.setToBlackList();
            User user = userRepository.save(result);

            return UserBlackRes.builder()
                    .courseNum(user.getCourseNum())
                    .name(user.getName())
                    .status(user.isStatus())
                    .role(user.getRole())
                    .build();
        }

        return null;
    }
}
