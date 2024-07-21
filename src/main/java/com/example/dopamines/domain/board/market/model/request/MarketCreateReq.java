package com.example.dopamines.domain.board.market.model.request;

import lombok.Data;

@Data
public class MarketCreateReq {
    private String title;
    private String content;
    private Integer price;
}
