package com.example.dopamines.domain.chat.model.dto;

import com.example.dopamines.domain.board.market.model.dto.MarketBoardDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
public class ChatRoomDTO {
    @Getter
    @Builder
    @AllArgsConstructor
    public static class Request {
        private String name;
        private Long receiverIdx;
        private Long marketPostIdx;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class Response {
        private String idx; // 채팅방 uuid
        private String name;
        private MarketBoardDTO.Response product;
    }
}
