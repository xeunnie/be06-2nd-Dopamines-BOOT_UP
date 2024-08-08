package com.example.dopamines.domain.board.market.model.response;

import java.time.LocalDate;
import java.util.List;
import lombok.Data;

@Data
public class MarketDetailRes {
    private Long idx;
    private String title;
    private Integer price;

    private String content;
    private List<String> images;
    private LocalDate createdAt;
    private Boolean status; // 판매 상태
    private String author;
    private Long authorIdx;
    private Boolean marked; // 유저에 따른 찜 상태
}
