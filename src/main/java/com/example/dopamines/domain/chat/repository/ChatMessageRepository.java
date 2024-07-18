package com.example.dopamines.domain.chat.repository;

import com.example.dopamines.domain.chat.model.entity.ChatMessage;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    @Query("SELECT m FROM ChatMessage m JOIN FETCH m.chatRoom c JOIN FETCH m.sender s WHERE c.idx = :chatRoomIdx")
    List<ChatMessage> findAllById(String chatRoomIdx);
}
