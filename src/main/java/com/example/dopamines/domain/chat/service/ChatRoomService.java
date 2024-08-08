  package com.example.dopamines.domain.chat.service;

import static com.example.dopamines.global.common.BaseResponseStatus.MARKET_ERROR_CONVENTION;
import static com.example.dopamines.global.common.BaseResponseStatus.MARKET_NOT_FOUND;

import com.example.dopamines.domain.board.market.mapper.MarketPostMapper;
import com.example.dopamines.domain.board.market.model.entity.MarketPost;
import com.example.dopamines.domain.board.market.model.response.MarketReadRes;
import com.example.dopamines.domain.board.market.repository.MarketPostRepository;
import com.example.dopamines.domain.chat.mapper.ChatMessageMapper;
import com.example.dopamines.domain.chat.mapper.ChatRoomMapper;
import com.example.dopamines.domain.chat.model.entity.ChatMessage;
import com.example.dopamines.domain.chat.model.entity.ChatRoom;
import com.example.dopamines.domain.chat.model.entity.ParticipatedChatRoom;
import com.example.dopamines.domain.chat.model.request.ChatRoomReq;

import com.example.dopamines.domain.chat.model.response.ChatMessageRes;
import com.example.dopamines.domain.chat.model.response.ChatRoomRes;
import com.example.dopamines.domain.chat.repository.ChatMessageRepository;
import com.example.dopamines.domain.chat.repository.ChatRoomRepository;
import com.example.dopamines.domain.chat.repository.ParticipatedChatRoomRepository;
import com.example.dopamines.domain.user.model.entity.User;
import com.example.dopamines.domain.user.repository.UserRepository;
import com.example.dopamines.global.common.BaseException;
import com.example.dopamines.global.common.BaseResponse;
import com.example.dopamines.global.common.BaseResponseStatus;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final ParticipatedChatRoomRepository participatedChatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final MarketPostRepository marketPostRepository;

    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;

    private final ChatRoomMapper chatRoomMapper;

    private final ChatMessageMapper chatMessageMapper;
    private final MarketPostMapper marketPostMapper;

    public List<ChatRoomRes> findAll(User user) { // 구매자
        List<ParticipatedChatRoom> chatRooms = participatedChatRoomRepository.findAllByUser(user);
        List<ChatRoomRes> dto = chatRooms.stream().map((room) ->{
            Long postIdx = room.getChatRoom().getMarketPost().getIdx(); // 게시글 idx
            MarketPost post = marketPostRepository.findByIdWithImages(postIdx); // 포스트
            String author = post.getUser().getName(); // 판매자

            return ChatRoomRes.builder()
                    .idx(room.getChatRoom().getIdx())
                    .product(marketPostMapper.toDto(post,author)) //author
                    .buyer(room.getChatRoom().getBuyer())
                    .updatedAt(room.getChatRoom().getUpdatedAt())
                    .build();

        }

        ).collect(Collectors.toList());

        return dto;
    }

    public ChatRoomRes create(ChatRoomReq req, User buyer) {
        // chat room 생성 -> uuid
        User seller = User.builder()
                .idx(req.getReceiverIdx())
                .build();

        MarketPost marketPost = marketPostRepository.findById(req.getMarketPostIdx())
                .orElseThrow(() -> new BaseException(MARKET_NOT_FOUND));


        ChatRoom chatRoom = chatRoomMapper.toEntity(buyer.getName(), marketPost);
        chatRoomRepository.save(chatRoom);

        // participate repository에 추가 - sender, receiver 모두
        ParticipatedChatRoom senderRoom = ParticipatedChatRoom.builder()
                .chatRoom(chatRoom)
                .user(buyer)
                .build();

        ParticipatedChatRoom receiverRoom = ParticipatedChatRoom.builder()
                .chatRoom(chatRoom)
                .user(seller)
                .build();

        participatedChatRoomRepository.save(senderRoom);
        participatedChatRoomRepository.save(receiverRoom);

        MarketReadRes res = marketPostMapper.toDto(marketPost, marketPost.getUser().getName());
        return chatRoomMapper.toDto(chatRoom, res);
    }

    public List<ChatMessageRes> getAllMessage(String roomId) {
        List<ChatMessage> messages = chatMessageRepository.findAllById(roomId);
        List<ChatMessageRes> responses = messages.stream().map(
                (message) -> chatMessageMapper.toDto(message, message.getSender().getName(), message.getSender().getIdx())
        ).collect(Collectors.toList());
        return responses;
    }
}
