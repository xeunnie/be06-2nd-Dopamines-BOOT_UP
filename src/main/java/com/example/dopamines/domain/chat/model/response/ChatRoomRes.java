package com.example.dopamines.domain.chat.model.response;

import com.example.dopamines.domain.board.market.model.response.MarketReadRes;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ChatRoomRes {
    private String idx; // 채팅방 uuid
    private String name;
    private MarketReadRes product;
}
