package com.example.dopamines.domain.board.community.open.repository;

import com.example.dopamines.domain.board.community.open.model.entity.OpenComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OpenCommentRepository extends JpaRepository<OpenComment,Long> {
}
