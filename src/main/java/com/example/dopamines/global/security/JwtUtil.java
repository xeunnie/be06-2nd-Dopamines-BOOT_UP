package com.example.dopamines.global.security;

import com.example.dopamines.domain.user.repository.UserRepository;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jwts.SIG;
import io.jsonwebtoken.security.SignatureException;
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
    private final Long REFRESH_EXPIRE = 1000L * 60 * 60 * 24 * 14; // 14일

    public JwtUtil(@Value("${spring.jwt.secret}") String secretKey, UserRepository userRepository) {
        this.secretKey = new SecretKeySpec(
                secretKey.getBytes(StandardCharsets.UTF_8),
                SIG.HS256.key().build().getAlgorithm()
        );
        this.customUserDetailService = new CustomUserDetailService(userRepository);
    }

    public String createToken(Long idx, String email, String role,String nickname) {
        return Jwts.builder()
                .claim("idx",idx)
                .claim("email",email)
                .claim("role",role)
                .claim("nickname",nickname)
                .issuedAt(new Date(System.currentTimeMillis())) //생성시간
                .expiration(new Date(System.currentTimeMillis()+ 20 * 1000))   //만료시간
                .signWith(secretKey) //제일 중요 -> 우리만 알 수 있는 secretKey
                .compact();
    }

    public String createRefreshToken(Long idx, String email, String role,String nickname) {
        return Jwts.builder()
                .claim("idx",idx)
                .claim("email",email)
                .claim("role",role)
                .claim("nickname",nickname)
                .issuedAt(new Date(System.currentTimeMillis())) //생성시간
                .expiration(new Date(System.currentTimeMillis() + REFRESH_EXPIRE))   //만료시간
                .signWith(secretKey) //제일 중요 -> 우리만 알 수 있는 secretKey
                .compact();
    }

    public String createTokenAllowTranslate(Long idx,String nickname) {
        return Jwts.builder()
                .claim("idx",idx)
                .claim("nickname",nickname)
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

    public String getNickname(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("nickname", String.class);
    }

    public Boolean isExpired(String token) {
        try {
            return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
        } catch (Exception e) {
            return true;
        }
    }

    public Boolean isValid(String token) {
        try{
            return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().after(new Date());
        } catch (ExpiredJwtException | SignatureException jwtException){ // 토큰 만료, 서명 검증 실패
            return false;
        }
    }

    public Authentication getAuthentication(String jwtToken) {
        UserDetails userDetails = customUserDetailService.loadUserByUsername(getUsername(jwtToken));
        return new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
    }
}
