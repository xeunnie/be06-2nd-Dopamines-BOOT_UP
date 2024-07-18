package com.example.dopamines.domain.board.community.open.model.entity;

import com.example.dopamines.domain.user.model.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OpenBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    private String title;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_idx")
    private User user;

    private String image;

    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "openBoard")
    List<OpenLike> likes;

    @OneToMany(mappedBy = "openBoard")
    private List<OpenComment> comments;
}
