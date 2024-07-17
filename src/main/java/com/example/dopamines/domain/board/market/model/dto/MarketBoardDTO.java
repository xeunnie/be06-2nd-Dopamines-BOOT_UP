package com.example.dopamines.domain.board.market.model.dto;

import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class MarketBoardDTO {
    @Getter
    @Builder
    @AllArgsConstructor
    public static class Request {
        private String title;
        private String content;
        private Integer price;
    }

    @Getter
    @AllArgsConstructor
    public static class Response {
        private Long idx;
        private String title;
        private Integer price;
        private LocalDate createdAt;
        private String author;
        private String mainImage;
        private Boolean status;
    }

    @Getter
    @AllArgsConstructor
    public static class DetailResponse {
        private Long idx;
        private String title;
        private Integer price;

        private String content;
        private List<String> images;
        private LocalDate createdAt;
        private Boolean status; // 판매 상태
        private String author;
        private Boolean marked; // 유저에 따른 찜 상태

        public void setStatus(Boolean value) {
            status = value;
        }

        public void setMarked(Boolean value) {
            marked = value;
        }
    }
}
