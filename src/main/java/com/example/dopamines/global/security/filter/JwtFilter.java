package com.example.dopamines.global.security.filter;

import com.example.dopamines.domain.user.model.entity.User;
import com.example.dopamines.global.security.CustomUserDetails;
import com.example.dopamines.global.security.JwtUtil;
import com.example.dopamines.global.security.jwt.service.RefreshTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authorization = null;
        String refreshToken = null;
        if(request.getHeader("Authorization") != null){
            authorization = request.getHeader("Authorization");
        }

        if(request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("JwtToken")) {
                    authorization = cookie.getValue();
                }

                if (cookie.getName().equals("RefreshToken")) {
                    refreshToken = cookie.getValue();
                }
            }
        }

        // 토큰이 없거나, "Bearer "로 시작하지 않으면 다음 필터로 넘기기
        if(authorization == null){
            System.out.println("authorization 이 null임");
            filterChain.doFilter(request,response); //다음 필터로 넘어가기
            return;
        }

        String token = authorization.split(" ")[0];

        if(jwtUtil.isExpired(token)){ // 토큰 만료 검증
            System.out.println("토큰 만료됨");
            if (refreshToken == null) { // refresh 토큰이 없을 때
                System.out.println("refresh token이 없음");
                filterChain.doFilter(request,response);
                return;
            }

            String reissuedAccessToken = refreshTokenService.reissueAccessToken(refreshToken);
            if (reissuedAccessToken == null) { // client의 refresh token이 변조되었거나, 만료되었거나, 서버가 가지고있는 refreshtoken과 다르거나
                System.out.println("refresh token이 null임");
                filterChain.doFilter(request, response);
                return;
            }

            token = reissuedAccessToken;
            Cookie cookie = new Cookie("JwtToken", token);
            cookie.setHttpOnly(true);
            cookie.setSecure(true);
            cookie.setPath("/");
            response.addCookie(cookie);

            refreshToken = refreshTokenService.reissueRefreshToken(refreshToken); //RTR 적용
            Cookie reissuedRefreshToken = new Cookie("RefreshToken", refreshToken);
            reissuedRefreshToken.setHttpOnly(true);
            reissuedRefreshToken.setSecure(true);
            reissuedRefreshToken.setPath("/");
            response.addCookie(reissuedRefreshToken);
        }

        //정상 토큰 확인
        Long idx = jwtUtil.getIdx(token);
        String username = jwtUtil.getUsername(token);
        String role = jwtUtil.getRole(token);

        //임시적인 멤버 객체 생성
        User user = User.builder()
                .idx(idx)
                .email(username)
                .role(role)
                .build();

        // 직접 CustomDetails 객체로 변환
        CustomUserDetails customUserDetails = new CustomUserDetails(user);

        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails,null,customUserDetails.getAuthorities());
        //ContextHolder 에 미리 심어줌으로서, LoginFilter가 로그인 된 사용자라고 판명
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request,response);
    }
}
