package com.example.dopamines.domain.chat.model.dto;

import lombok.Data;

@Data
public class ChatMessageDTO {
    private String content;
    private String sender;
    private MessageType type;
    public enum MessageType {
        CHAT, JOIN, LEAVE
    }
}
