package com.example.dopamines.domain.board.community.open.model.request;

import com.example.dopamines.domain.user.model.entity.User;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OpenLikeReq {
    private Long OpenBoardIdx;
    private User user;
}
