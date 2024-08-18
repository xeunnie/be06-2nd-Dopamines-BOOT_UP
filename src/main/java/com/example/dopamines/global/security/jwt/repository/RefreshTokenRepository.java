package com.example.dopamines.global.security.jwt.repository;

import com.example.dopamines.global.security.jwt.model.RefreshToken;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByEmail(String email);
    void deleteByEmail(String email);
}
