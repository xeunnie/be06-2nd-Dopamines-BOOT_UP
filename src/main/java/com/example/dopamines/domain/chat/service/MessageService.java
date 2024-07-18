package com.example.dopamines.domain.chat.service;

import com.example.dopamines.domain.chat.mapper.ChatMessageMapper;
import com.example.dopamines.domain.chat.model.dto.ChatMessageDTO.Request;
import com.example.dopamines.domain.chat.model.dto.ChatMessageDTO.Response;
import com.example.dopamines.domain.chat.model.entity.ChatMessage;
import com.example.dopamines.domain.chat.model.entity.ChatRoom;
import com.example.dopamines.domain.chat.repository.ChatMessageRepository;
import com.example.dopamines.domain.chat.repository.ChatRoomRepository;
import com.example.dopamines.domain.user.model.entity.User;
import com.example.dopamines.domain.user.repository.UserRepository;
import com.example.dopamines.global.common.BaseException;
import com.example.dopamines.global.common.BaseResponseStatus;
import com.example.dopamines.global.security.CustomUserDetails;
import com.example.dopamines.global.security.JwtUtil;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final JwtUtil jwtUtil;
    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageMapper chatMessageMapper;

    private final UserRepository userRepository;

    public Response sendMessage(String roomId, String bearerToken, Request chatMessage) {
        ChatRoom chatRoom = chatRoomRepository.findById(roomId).orElseThrow(()->new BaseException(BaseResponseStatus.MARKET_ERROR_CHATROOM_NOT_FOUND));

        Long senderIdx = jwtUtil.getIdx(bearerToken);
        User sender = userRepository.findById(senderIdx).orElseThrow(() -> new BaseException(BaseResponseStatus.USER_NOT_FOUND));

        ChatMessage message = chatMessageMapper.toEntity(chatMessage, sender, chatRoom);
        chatMessageRepository.save(message);

        return chatMessageMapper.toDto(message, sender.getName());
    }

}
