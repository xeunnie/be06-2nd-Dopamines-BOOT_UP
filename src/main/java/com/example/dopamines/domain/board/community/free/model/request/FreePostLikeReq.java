package com.example.dopamines.domain.board.community.free.model.request;

import com.example.dopamines.domain.user.model.entity.User;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FreePostLikeReq {
    private Long FreePostIdx;
    private User user;
}
