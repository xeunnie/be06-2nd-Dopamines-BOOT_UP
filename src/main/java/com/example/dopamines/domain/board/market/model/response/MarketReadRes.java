package com.example.dopamines.domain.board.market.model.response;

import java.time.LocalDate;
import lombok.Data;

@Data
public class MarketReadRes {
    private Long idx;
    private String title;
    private Integer price;
    private LocalDate createdAt;
    private String author;
    private String mainImage;
    private Boolean status;
}
