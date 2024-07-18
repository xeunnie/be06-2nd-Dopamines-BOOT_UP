package com.example.dopamines.domain.chat.repository;

import com.example.dopamines.domain.chat.model.entity.ParticipatedChatRoom;
import com.example.dopamines.domain.user.model.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipatedChatRoomRepository extends JpaRepository<ParticipatedChatRoom, Long> {
    List<ParticipatedChatRoom> findAllByUser(User user);
}
