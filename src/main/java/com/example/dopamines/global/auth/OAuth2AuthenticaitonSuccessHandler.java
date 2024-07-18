package com.example.dopamines.global.auth;


import com.example.dopamines.domain.user.model.entity.User;

import com.example.dopamines.domain.user.repository.UserRepository;
import com.example.dopamines.global.security.JwtUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticaitonSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        Map<String,Object> properties = (Map<String, Object>) oAuth2User.getAttributes().get("properties");
        String nickname = (String) properties.get("nickname");

        Optional<User> result = userRepository.findByEmail(nickname);

        User user = null;

        // 회원 조회 후 DB에 없으면 회원가입, 있으면 데이터 가져오기
        if(!result.isPresent()){
            LocalDateTime localDateTime = LocalDateTime.now();
            localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            user = User.builder()
                    .name(nickname)
                    .email(nickname)
                    .password("kakao")
                    .nickname(nickname)
                    .address("")
                    .phoneNumber("")
                    .createdAt(localDateTime)
                    .updatedAt(localDateTime)
                    .role("ROLE_USER")
                    .build();
            user.setSocialLogined(true);
            // Todo : 카카오로 회원가입하면 email을 받을 수 없어서 메일요청 불가 -> 그냥 바로 활성화
            user.setActiveOn(true);
            userRepository.save(user);

            // Todo : 카카오로 로그인 시, 추가정보 입력하는 회원가입 창 리다이렉트 필요
//            getRedirectStrategy().sendRedirect(request,response,"https://github.com/DopaminesBeyond/MiniPrjPrepare");

        } else{
            user = result.get();
        }
        Long idx = user.getIdx();
        String role = user.getRole();
        //Todo : 임시 닉네임 -> 변경 필요
        String nick = user.getNickname();

        //토큰 발급
        String token = jwtUtil.createToken(idx,nickname,role,nick);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION,"Bearer " + token);

        Cookie kToken = new Cookie("kToken",token);
        kToken.setHttpOnly(true);
        kToken.setSecure(true);
        kToken.setPath("/");
        kToken.setMaxAge(60601);
        response.addCookie(kToken);

        // 로그인이 성공하면, 리다이렉트 해 줄 주소
        getRedirectStrategy().sendRedirect(request,response,"https://github.com/DopaminesBeyond/MiniPrjPrepare");
    }
}
