package com.example.dopamines.domain.chat.model.request;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatMessageReq {
    public enum MessageType {
        CHAT, JOIN, LEAVE
    }
    private MessageType type;

    private String roomId;
    private String content;
    private String sender; // 보내는 사람
    private Long senderIdx; // 보내는 사람 idx
    private LocalDate createdAt;
}
