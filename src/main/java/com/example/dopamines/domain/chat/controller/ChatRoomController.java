package com.example.dopamines.domain.chat.controller;

import com.example.dopamines.domain.chat.model.request.ChatRoomReq;
import com.example.dopamines.domain.chat.model.response.ChatMessageRes;
import com.example.dopamines.domain.chat.model.response.ChatRoomRes;
import com.example.dopamines.domain.chat.service.ChatRoomService;
import com.example.dopamines.domain.user.model.entity.User;
import com.example.dopamines.global.common.BaseResponse;
import com.example.dopamines.global.common.annotation.CheckAuthentication;
import com.example.dopamines.global.security.CustomUserDetails;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatRoomController {
    private final ChatRoomService chatRoomService;

    // 채팅방을 만들어서 반환하는 기능
    @PostMapping(value = "/room")
    @CheckAuthentication
    public ResponseEntity<BaseResponse<ChatRoomRes>> createRoom(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody ChatRoomReq req) {
        User sender = customUserDetails.getUser();
        ChatRoomRes res = chatRoomService.create(req, sender);
        return ResponseEntity.ok(new BaseResponse<>(res));
    }

     // 현재 접속한 유저가 속한 채팅룸 리스트 받아오는 기능
    @GetMapping("/rooms")
    @CheckAuthentication
    public ResponseEntity<BaseResponse<List<ChatRoomRes>>> getChatRoomList(@AuthenticationPrincipal CustomUserDetails customUserDetails){
        User user = customUserDetails.getUser();
        List<ChatRoomRes> participatedRooms = chatRoomService.findAll(user);
        return ResponseEntity.ok(new BaseResponse<>(participatedRooms));
    }

    // 채팅방 메시지들을 받아오는 기능
    @GetMapping("/rooms/{roomId}/messages")
    public ResponseEntity<BaseResponse<List<ChatMessageRes>>> getMessages(@PathVariable String roomId) {
        List<ChatMessageRes> messages = chatRoomService.getAllMessage(roomId);
        return ResponseEntity.ok(new BaseResponse<>(messages));
    }

    //TODO: 채팅방 삭제 or 나가기 기능
}
