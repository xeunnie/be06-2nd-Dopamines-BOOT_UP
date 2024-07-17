package com.example.dopamines.domain.user.repository;

import com.example.dopamines.domain.user.model.entity.UserEmailVerify;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserEmailVerifyRepository extends JpaRepository<UserEmailVerify, Long> {
    Optional<UserEmailVerify> findByEmailAndUuid(String email, String uuid);
}
