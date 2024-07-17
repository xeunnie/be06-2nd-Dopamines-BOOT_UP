package com.example.dopamines.domain.user.repository;

import com.example.dopamines.domain.user.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
