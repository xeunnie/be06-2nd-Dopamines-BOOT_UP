package com.example.dopamines.global.security.filter;

import com.example.dopamines.global.security.CustomUserDetails;
import com.example.dopamines.global.security.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StreamUtils;

@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        UserLoginRequest dto;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ServletInputStream inputStream = request.getInputStream();
            String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
            dto = objectMapper.readValue(messageBody, UserLoginRequest.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String username = dto.getEmail();
        String password = dto.getPassword();

        // 그림에서 2번
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(username, password, null);

        // 그림에서 3번
        return authenticationManager.authenticate(authToken);

    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        CustomUserDetails user = (CustomUserDetails)authResult.getPrincipal();
        //권한 받아오기
        Collection<? extends GrantedAuthority> authorities = authResult.getAuthorities();
        //권한 받아오기
        GrantedAuthority auth = authorities.iterator().next();
        String role = auth.getAuthority();
        String username = user.getUsername();
        Long idx = user.getUser().getIdx();

        String token = jwtUtil.createToken(idx, username,role);
        System.out.println(token);

        response.addHeader("Authorization","Bearer " + token);

        Cookie jwtToken = new Cookie("JwtToken", token);
        response.addCookie(jwtToken);
    }
}
