package com.example.dopamines.domain.board.community.open.model.request;

import com.example.dopamines.domain.user.model.entity.User;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OpenPostLikeReq {
    private Long OpenPostIdx;
    private User user;
}
