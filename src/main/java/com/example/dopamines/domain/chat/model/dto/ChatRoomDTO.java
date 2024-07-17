package com.example.dopamines.domain.chat.model.dto;

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
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class Response {
        private String idx; // 채팅방 uuid
        private String name;
    }
}
