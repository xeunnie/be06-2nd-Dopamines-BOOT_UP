package com.example.dopamines.domain.chat.controller;


import com.example.dopamines.domain.chat.model.request.ChatMessageReq;
import com.example.dopamines.domain.chat.service.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;


@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatController {

    private final KafkaTemplate<String, ChatMessageReq> kafkaTemplate;
    private final SimpMessagingTemplate messagingTemplate;
    private final MessageService messageService;
    private final ObjectMapper objectMapper;

    @MessageMapping("/chat.sendMessage/{roomId}")
    public void sendMessage(@DestinationVariable String roomId, /*@Header("Authorization") String authHeader ,*/ @Payload ChatMessageReq chatMessage) {
        log.info("[SENDER - {}] messages : {}", chatMessage.getSender(), chatMessage.getContent());
//        String bearerToken = authHeader.split(" ")[1];
        chatMessage = messageService.sendMessage(/*bearerToken,*/ chatMessage); // db 저장
        kafkaTemplate.send("chat-room", chatMessage); // 카프카에 메시지 전송
    }

    @KafkaListener(topicPattern = "chat-room")
    public void consumeMessage(ConsumerRecord<String, Object> record) throws JsonProcessingException {
        String message = objectMapper.writeValueAsString(record.value());
        ChatMessageReq sendMessageReq = objectMapper.readValue(message, ChatMessageReq.class);
        messagingTemplate.convertAndSend("/sub/room/" + sendMessageReq.getRoomId(), sendMessageReq);
    }
}
