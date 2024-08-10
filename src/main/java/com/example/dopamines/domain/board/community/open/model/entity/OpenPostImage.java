package com.example.dopamines.domain.board.community.open.model.entity;

import com.example.dopamines.domain.board.community.free.model.entity.FreePost;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OpenPostImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="open_post_idx")
    private OpenPost openPost;
}
