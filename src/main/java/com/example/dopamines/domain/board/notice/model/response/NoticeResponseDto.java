package com.example.dopamines.domain.board.notice.model.response;

import com.example.dopamines.domain.board.notice.model.entity.Notice;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoticeResponseDto {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime date;
    private String category;
    private boolean isPrivate;
    private List<String> imageUrls;

    public NoticeResponseDto(Notice savedNotice) {
    }

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