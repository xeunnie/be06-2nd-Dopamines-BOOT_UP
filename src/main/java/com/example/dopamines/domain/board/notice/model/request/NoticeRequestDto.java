package com.example.dopamines.domain.board.notice.model.request;

import com.example.dopamines.domain.board.notice.model.entity.Notice;
import jakarta.persistence.ElementCollection;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class NoticeRequestDto {
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
