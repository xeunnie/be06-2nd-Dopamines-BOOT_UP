package com.example.dopamines.domain.board.community.open.repository;

import com.example.dopamines.domain.board.community.free.model.entity.FreePostImage;
import com.example.dopamines.domain.board.community.open.model.entity.OpenPostImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OpenPostImageRepository extends JpaRepository<OpenPostImage, Long> {
}
