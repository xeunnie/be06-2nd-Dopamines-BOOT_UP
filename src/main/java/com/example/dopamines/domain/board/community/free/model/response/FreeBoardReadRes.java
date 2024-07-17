package com.example.dopamines.domain.board.community.free.model.response;

import com.example.dopamines.domain.user.model.entity.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
public class FreeBoardReadRes {
    private Long idx;
    private String title;
    private String content;
    private User author;
    private String image;
    private LocalDateTime created_at;
}
