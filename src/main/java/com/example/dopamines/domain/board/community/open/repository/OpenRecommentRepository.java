package com.example.dopamines.domain.board.community.open.repository;

import com.example.dopamines.domain.board.community.open.model.entity.OpenRecomment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OpenRecommentRepository extends JpaRepository<OpenRecomment,Long> {
}
