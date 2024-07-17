package com.example.dopamines.domain.user.service;

import com.example.dopamines.domain.user.model.entity.User;
import com.example.dopamines.domain.user.model.request.UserSignupRequest;
import com.example.dopamines.domain.user.model.response.UserSignupResponse;
import com.example.dopamines.domain.user.repository.UserRepository;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserSignupResponse signup(UserSignupRequest request) {
        LocalDateTime localDateTime = LocalDateTime.now();
        localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        User member = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(bCryptPasswordEncoder.encode(request.getPassword()))
                .nickname(request.getNickname())
                .address(request.getAddress())
                .phoneNumber(request.getPhoneNumber())
                .createdAt(localDateTime)
                .updatedAt(localDateTime)
                .role("ROLE_USER")
                .build();
        User result = userRepository.save(member);

        if(result == null){
            return null;
        }
        return new UserSignupResponse().toDto(result);
    }
}
