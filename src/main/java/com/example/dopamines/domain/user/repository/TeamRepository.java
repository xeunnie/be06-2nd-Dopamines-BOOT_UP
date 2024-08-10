package com.example.dopamines.domain.user.repository;

import com.example.dopamines.domain.user.model.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team,Long> {
    Optional<List<Team>> findByCourseNum(Integer courseNum);
}