package com.example.dopamines.domain.board.community.free.model.entity;

import com.example.dopamines.domain.user.model.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

@DynamicInsert
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FreePost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    private String title;

    @Column(name = "content", length = 65535) // length 속성을 사용하여 길이를 늘림
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_idx")
    private User user;

//    @ElementCollection
//    private List<String> imageUrlList;

    private LocalDateTime createdAt;

//    @OneToMany(mappedBy = "freePost")
//    List<FreePostLike> likes;

    @ColumnDefault(value = "0")
    private Integer likesCount;

    @Version    // 낙관적 락 : 프로그램 단에서 사용하는 Lock, 낙관적 락 테스트할 때는 비관적 락 주석 처리
    @ColumnDefault(value = "0")
    private Integer version;

    public void addLikesCount() {
        this.likesCount = this.likesCount + 1;
    }
    public void subLikesCount() {
        this.likesCount = this.likesCount - 1;
    }

    @OneToMany(mappedBy = "freePost")
    private List<FreeComment> comments;

    @OneToMany(mappedBy = "freePost")
    private List<FreePostImage> images;
}
