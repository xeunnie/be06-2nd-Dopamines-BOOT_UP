package com.example.dopamines.domain.chat.repository;

import com.example.dopamines.domain.chat.model.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
}
