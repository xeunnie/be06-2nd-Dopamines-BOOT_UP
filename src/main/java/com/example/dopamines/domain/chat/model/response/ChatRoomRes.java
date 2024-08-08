package com.example.dopamines.domain.chat.model.response;

import com.example.dopamines.domain.board.market.model.response.MarketReadRes;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ChatRoomRes {
    private String idx; // 채팅방 uuid
//    private String name;
    private String buyer;
    private MarketReadRes product;
    private LocalDate updatedAt;
}
