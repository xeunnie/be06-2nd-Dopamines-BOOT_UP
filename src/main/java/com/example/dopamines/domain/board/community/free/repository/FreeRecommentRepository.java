package com.example.dopamines.domain.board.community.free.repository;

import com.example.dopamines.domain.board.community.free.model.entity.FreeRecomment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FreeRecommentRepository extends JpaRepository<FreeRecomment,Long> {
}
