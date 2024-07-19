package com.example.dopamines.domain.chat.model.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
public class ChatMessageDTO {
    public enum MessageType {
        CHAT, JOIN, LEAVE
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class Request {
        private String content;
        private String sender;
        private MessageType type;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class Response {
        private String content;
        private String senderName;
        private LocalDate createdAt;
    }

}
