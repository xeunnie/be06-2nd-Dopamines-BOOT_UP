package com.example.dopamines.domain.chat.model.entity;

import com.example.dopamines.domain.board.market.model.entity.MarketPost;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ChatRoom {
    @Id
    private String idx;
    private String name;
    private boolean status;
    private LocalDate createdAt;
    private LocalDate updatedAt;

    @OneToMany(mappedBy = "chatRoom", fetch = FetchType.LAZY)
    private List<ChatMessage> messages;

    @ManyToOne
    @JoinColumn(name="market_board_idx")
    private MarketPost marketPost;
}

