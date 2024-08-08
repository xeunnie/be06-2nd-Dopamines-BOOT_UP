package com.example.dopamines.domain.chat.repository;

import com.example.dopamines.domain.board.market.model.entity.MarketPost;
import com.example.dopamines.domain.chat.model.entity.ChatRoom;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, String> {
    boolean existsAllByBuyerAndMarketPost(String buyer, MarketPost marketPost);
}
