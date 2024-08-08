package com.example.dopamines.global.security.filter;

import com.example.dopamines.domain.user.model.entity.User;
import com.example.dopamines.global.security.CustomUserDetails;
import com.example.dopamines.global.security.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    
    private final JwtUtil jwtUtil;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
//        String authorization = request.getHeader("Authorization");
        String authorization = "";
        if(request.getHeader("Authorization") == null){
            authorization = null;
        } else{
            authorization = request.getHeader("Authorization");
        }
        if(request.getCookies() != null)
        for(Cookie cookie : request.getCookies()){
            if(cookie.getName().equals("JwtToken")){
                authorization = cookie.getValue();

            }
        }

        // 토큰이 없거나, "Bearer "로 시작하지 않으면 다음 필터로 넘기기
        if(authorization == null){
//            System.out.println("Bearer 토큰이 없음");
            System.out.println("authorization 이 null임");
            //다음 필터로 넘어가기
            filterChain.doFilter(request,response);
            return;
        }

        // 토큰 만료 검증
        if(authorization != null) {
            String token = authorization.split(" ")[0];
//            String token = authorization;

            if(jwtUtil.isExpired(token)){
                System.out.println("토큰 만료됨");
                filterChain.doFilter(request,response);
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
        }

        filterChain.doFilter(request,response);
    }
}
