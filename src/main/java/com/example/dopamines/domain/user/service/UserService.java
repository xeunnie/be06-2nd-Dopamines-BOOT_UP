package com.example.dopamines.domain.user.service;

import com.example.dopamines.domain.user.model.entity.Team;
import com.example.dopamines.domain.user.model.entity.User;
import com.example.dopamines.domain.user.model.request.UserSignupReq;
import com.example.dopamines.domain.user.model.response.UserActiveOnRes;
import com.example.dopamines.domain.user.model.response.UserSignupRes;
import com.example.dopamines.domain.user.repository.TeamRepository;
import com.example.dopamines.domain.user.repository.UserRepository;
import com.example.dopamines.global.common.BaseException;
import com.example.dopamines.global.common.BaseResponseStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final TeamRepository teamRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserSignupRes signup(UserSignupReq request) {
        LocalDateTime localDateTime = LocalDateTime.now();
        localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(bCryptPasswordEncoder.encode(request.getPassword()))
                .nickname(request.getNickname())
                .address(request.getAddress())
                .phoneNumber(request.getPhoneNumber())
                .createdAt(localDateTime)
                .updatedAt(localDateTime)
                .role("ROLE_TEMPORARY_USER")
                .build();
        User result = userRepository.save(user);

        if(result == null){
            throw new BaseException(BaseResponseStatus.USER_INVALID_SINGUP_REQUEST);
        }
        return new UserSignupRes().toDto(result);
    }

    public UserActiveOnRes setActiveOn(String email) {
        User findUser = userRepository.findByEmail(email).orElseThrow(() -> new BaseException(BaseResponseStatus.USER_NOT_FOUND));
        if(findUser != null){
            findUser.setActiveOn(true);
            User user = userRepository.save(findUser);

            return UserActiveOnRes.builder()
                    .email(user.getEmail())
                    .name(user.getName())
                    .enabled(user.isEnabled())
                    .build();
        }

        return null;
    }
}
