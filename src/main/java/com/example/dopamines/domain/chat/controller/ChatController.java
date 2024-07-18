package com.example.dopamines.domain.chat.controller;

import com.example.dopamines.domain.chat.model.dto.ChatMessageDTO;
import com.example.dopamines.domain.chat.model.entity.ChatMessage;
import com.example.dopamines.domain.chat.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;


@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatController {

    private final MessageService messageService;

    @MessageMapping("/chat.sendMessage/{roomId}")
    @SendTo("/sub/{roomId}")
    public ChatMessageDTO.Response sendMessage(@DestinationVariable String roomId, @Header("Authorization") String authHeader , @Payload ChatMessageDTO.Request chatMessage) {
        log.info("[SENDER - {}] messages : {}", chatMessage.getSender(), chatMessage.getContent());
        String bearerToken = authHeader.split(" ")[1];
        ChatMessageDTO.Response res = messageService.sendMessage(roomId, bearerToken, chatMessage); // db에 저장하는 코드
        //TODO: 수신자에게 알림가게 해야함
        return res;
    }

//    @MessageMapping("/chat.addUser/{roomId}")
//    @SendTo("/topic/{roomId}")
//    public ChatMessage addUser(@DestinationVariable String roomId, @Payload ChatMessage chatMessage,
//                               SimpMessageHeaderAccessor headerAccessor) {
//        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
//        log.info("[ADDUSER - {}] ", chatMessage.getSender());
//        return chatMessage;
//    }
}
