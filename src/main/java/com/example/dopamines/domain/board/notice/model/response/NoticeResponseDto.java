package com.example.dopamines.domain.board.notice.model.response;

import com.example.dopamines.domain.board.notice.model.entity.Notice;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
        this.id = savedNotice.getId();
        this.title = savedNotice.getTitle();
        this.content = savedNotice.getContent();
        this.date = savedNotice.getDate();
        this.category = savedNotice.getCategory();
        this.isPrivate = savedNotice.isPrivate();
        this.imageUrls = savedNotice.getImageUrls();
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