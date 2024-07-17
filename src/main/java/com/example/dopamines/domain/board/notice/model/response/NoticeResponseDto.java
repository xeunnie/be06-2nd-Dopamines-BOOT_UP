package com.example.dopamines.domain.board.notice.model.response;

import com.example.dopamines.domain.board.notice.model.entity.Notice;
import com.example.dopamines.global.common.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class NoticeResponseDto  {
    private Long id;

    private String title;

    private String content;

    private LocalDateTime date;

    private String category;

    private boolean isPrivate;

    private List<String> imageUrls;

    public Notice toEntity() {
        return Notice.builder()
                .id(this.id)
                .title(this.title)
                .content(this.content)
                .date(this.date)
                .category(this.category)
                .isPrivate(this.isPrivate)
                .imageUrls(this.imageUrls)
                .build();
    }
}
