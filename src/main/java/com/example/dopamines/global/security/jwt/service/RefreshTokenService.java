package com.example.dopamines.global.security.jwt.service;

import com.example.dopamines.global.security.JwtUtil;
import com.example.dopamines.global.security.jwt.model.RefreshToken;
import com.example.dopamines.global.security.jwt.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtUtil jwtUtil;

    public void save(String username, String refreshToken) {
        RefreshToken existingRefreshToken = refreshTokenRepository.findByEmail(username).orElse(null);
        RefreshToken refreshTokenEntity;
        if (existingRefreshToken != null) {
            refreshTokenEntity = RefreshToken.builder()
                    .idx(existingRefreshToken.getIdx())
                    .refreshToken(refreshToken)
                    .email(username)
                    .build();
        } else {
            refreshTokenEntity = RefreshToken.builder()
                    .email(username)
                    .refreshToken(refreshToken)
                    .build();
        }
        refreshTokenRepository.save(refreshTokenEntity);
    }

    @Transactional
    public void delete(String refreshToken) {
        String email = jwtUtil.getUsername(refreshToken);
        if (email != null) {
            refreshTokenRepository.deleteByEmail(email);
        }
    }

    public String reissueAccessToken(String token) {
        if (jwtUtil.isExpired(token)) {
            log.info("refresh token 만료됨");
            return null;
        }

        String email = jwtUtil.getUsername(token);
        RefreshToken refreshTokenEntity = refreshTokenRepository.findByEmail(email).orElse(null);
        if (refreshTokenEntity != null) {
            String refreshToken = refreshTokenEntity.getRefreshToken();
            if (jwtUtil.isValid(refreshToken) && refreshToken.equals(refreshToken)){
                System.out.println("refresh token으로 재발급");
                return jwtUtil.createToken(jwtUtil.getIdx(refreshToken), jwtUtil.getUsername(refreshToken),
                        jwtUtil.getRole(refreshToken),
                        jwtUtil.getNickname(refreshToken));
            }
        }
        return null;
    }

    public String reissueRefreshToken(String token) {
        String reissuedRefreshToken = jwtUtil.createRefreshToken(jwtUtil.getIdx(token),jwtUtil.getUsername(token), jwtUtil.getRole(token),
                jwtUtil.getNickname(token));

        RefreshToken refreshToken = refreshTokenRepository.findByEmail(jwtUtil.getUsername(token)).get(); // 기존의 refresh 토큰
        refreshToken.updateRefreshToken(reissuedRefreshToken); // refresh token update
        refreshTokenRepository.save(refreshToken);

        return reissuedRefreshToken;
    }
}
