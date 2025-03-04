package com.example.dopamines.domain.board.project.repository;

import com.example.dopamines.domain.board.project.model.entity.ProjectPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectPostRepository extends JpaRepository<ProjectPost, Long> {

    Optional<List<ProjectPost>> findByCourseNum(Integer courseNum);
}