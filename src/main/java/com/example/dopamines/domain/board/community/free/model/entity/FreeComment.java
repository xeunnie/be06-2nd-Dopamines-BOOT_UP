package com.example.dopamines.domain.board.community.free.model.entity;

import com.example.dopamines.domain.user.model.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

@DynamicInsert
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FreeComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_idx")
    private User user;

    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_idx")
    private FreePost freePost;

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

//    @OneToMany(mappedBy = "freeComment", fetch = FetchType.LAZY)
//    @BatchSize(size = 10)
//    private List<FreeCommentLike> likes;

    @OneToMany(mappedBy = "freeComment")
    private List<FreeRecomment> freeRecomments;

}
