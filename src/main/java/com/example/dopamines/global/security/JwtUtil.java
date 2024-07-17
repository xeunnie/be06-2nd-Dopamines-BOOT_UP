package com.example.dopamines.global.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jwts.SIG;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    private SecretKey secretKey;
    private CustomUserDetailService customUserDetailService;

    public JwtUtil(@Value("${spring.jwt.secret}") String secretKey, UserRepository userRepository) {
        this.secretKey = new SecretKeySpec(
                secretKey.getBytes(StandardCharsets.UTF_8),
                SIG.HS256.key().build().getAlgorithm()
        );
        this.customUserDetailService = new CustomUserDetailService(userRepository);
    }

    public String createToken(Long idx, String email, String role) {
        return Jwts.builder()
                .claim("idx",idx)
                .claim("email",email)
                .claim("role",role)
                .issuedAt(new Date(System.currentTimeMillis())) //생성시간
                .expiration(new Date(System.currentTimeMillis()+ 200 * 60 * 1000))   //만료시간
                .signWith(secretKey) //제일 중요 -> 우리만 알 수 있는 secretKey
                .compact();
    }

    public Long getIdx(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("idx", Long.class);
    }

    public String getUsername(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("email", String.class);
    }

    public String getRole(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("role", String.class);
    }

    public Boolean isExpired(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
    }

    public Authentication getAuthentication(String jwtToken) {
        UserDetails userDetails = customUserDetailService.loadUserByUsername(getUsername(jwtToken));
        return new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
    }
}
