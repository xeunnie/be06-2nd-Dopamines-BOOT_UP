package com.example.dopamines.domain.chat.controller;

import com.example.dopamines.domain.chat.model.dto.ChatRoomDTO;
import com.example.dopamines.domain.chat.model.dto.ChatRoomDTO.Response;
import com.example.dopamines.domain.chat.model.entity.ChatMessage;
import com.example.dopamines.domain.chat.service.ChatRoomService;
import com.example.dopamines.domain.user.model.entity.User;
import com.example.dopamines.global.common.BaseResponse;
import com.example.dopamines.global.common.annotation.CheckAuthentication;
import com.example.dopamines.global.security.CustomUserDetails;
import java.util.ArrayList;
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
    public ResponseEntity<BaseResponse<Response>> createRoom(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody ChatRoomDTO.Request req) {
        User user = customUserDetails.getUser();
        Response res = chatRoomService.create(req, user);
        return ResponseEntity.ok(new BaseResponse<>(res));
    }

     // 현재 접속한 유저가 속한 채팅룸 리스트 받아오는 기능
    @GetMapping("/rooms")
    @CheckAuthentication
    public ResponseEntity<BaseResponse<List<Response>>> getChatRoomList(@AuthenticationPrincipal CustomUserDetails customUserDetails){
        User user = customUserDetails.getUser();
        List<Response> participatedRooms = chatRoomService.findAll(user);
        return ResponseEntity.ok(new BaseResponse<>(participatedRooms));
    }

    // 채팅방 메시지들을 받아오는 기능
    @GetMapping("/rooms/{roomId}/messages")
    public List<ChatMessage> getMessages(@PathVariable String roomId) {


        return chatRooms.getOrDefault(roomId, new ArrayList<>());
    }

    //TODO: 채팅방 삭제 or 나가기 기능
}
