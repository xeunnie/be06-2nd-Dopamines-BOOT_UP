package com.example.dopamines.domain.user.repository;

import com.example.dopamines.domain.user.model.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team,Long> {
}
