package com.example.dopamines.domain.board.community.free.repository;

import com.example.dopamines.domain.board.community.free.model.entity.FreeComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FreeCommentRepository extends JpaRepository<FreeComment,Long> {
}
