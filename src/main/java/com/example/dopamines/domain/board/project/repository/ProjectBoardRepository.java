package com.example.dopamines.domain.board.project.repository;

import com.example.dopamines.domain.board.project.model.entity.ProjectBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectBoardRepository extends JpaRepository<ProjectBoard, Long> {
    List<ProjectBoard> findByCourseNum(Long courseNum);
}