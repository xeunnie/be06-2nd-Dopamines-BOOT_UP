package com.example.dopamines.domain.board.community.free.model.entity;

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
public class FreePostImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="free_post_idx")
    private FreePost freePost;
}
