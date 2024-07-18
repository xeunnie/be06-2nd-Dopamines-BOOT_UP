package com.example.dopamines.domain.chat.repository;

import com.example.dopamines.domain.chat.model.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, String> {
}
