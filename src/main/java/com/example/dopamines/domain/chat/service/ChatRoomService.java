  package com.example.dopamines.domain.chat.service;

import static com.example.dopamines.global.common.BaseResponseStatus.MARKET_ERROR_USER_NOT_FOUND;

import com.example.dopamines.domain.chat.mapper.ChatMessageMapper;
import com.example.dopamines.domain.chat.mapper.ChatRoomMapper;
import com.example.dopamines.domain.chat.model.dto.ChatMessageDTO;
import com.example.dopamines.domain.chat.model.dto.ChatRoomDTO;
import com.example.dopamines.domain.chat.model.dto.ChatRoomDTO.Response;
import com.example.dopamines.domain.chat.model.entity.ChatMessage;
import com.example.dopamines.domain.chat.model.entity.ChatRoom;
import com.example.dopamines.domain.chat.model.entity.ParticipatedChatRoom;
import com.example.dopamines.domain.chat.repository.ChatMessageRepository;
import com.example.dopamines.domain.chat.repository.ChatRoomRepository;
import com.example.dopamines.domain.chat.repository.ParticipatedChatRoomRepository;
import com.example.dopamines.domain.user.model.entity.User;
import com.example.dopamines.domain.user.repository.UserRepository;
import com.example.dopamines.global.common.BaseException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final ParticipatedChatRoomRepository participatedChatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;

    private final ChatRoomMapper chatRoomMapper;

    private final ChatMessageMapper chatMessageMapper;

    public List<ChatRoomDTO.Response> findAll(User user) {
        List<ParticipatedChatRoom> chatRooms = participatedChatRoomRepository.findAllByUser(user);
        List<ChatRoomDTO.Response> dto = chatRooms.stream().map((room) ->
                ChatRoomDTO.Response.builder()
                        .idx(room.getChatRoom().getIdx())
                        .name(room.getChatRoom().getName())
                        .build()
        ).collect(Collectors.toList());

        return dto;
    }


    public ChatRoomDTO.Response create(ChatRoomDTO.Request req, User sender) {
        // chat room 생성 -> uuid
        User receiver = userRepository.findById(req.getReceiverIdx()).orElseThrow(()->new BaseException(MARKET_ERROR_USER_NOT_FOUND));
        ChatRoom chatRoom = chatRoomMapper.toEntity(req.getName());
        chatRoomRepository.save(chatRoom);

        // participate repository에 추가 - sender, receiver 모두
        ParticipatedChatRoom senderRoom = ParticipatedChatRoom.builder()
                .chatRoom(chatRoom)
                .user(sender)
                .build();

        ParticipatedChatRoom receiverRoom = ParticipatedChatRoom.builder()
                .chatRoom(chatRoom)
                .user(receiver)
                .build();

        participatedChatRoomRepository.save(senderRoom);
        participatedChatRoomRepository.save(receiverRoom);

        return chatRoomMapper.toDto(chatRoom);
    }

    public List<ChatMessageDTO.Response> getAllMessage(String roomId) {
        List<ChatMessage> messages = chatMessageRepository.findAllById(roomId);
        List<ChatMessageDTO.Response> responses = messages.stream().map(
                (message) -> chatMessageMapper.toDto(message, message.getSender().getName())
        ).collect(Collectors.toList());
        return responses;
    }
}
