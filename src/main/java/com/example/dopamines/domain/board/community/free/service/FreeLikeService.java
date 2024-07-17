package com.example.dopamines.domain.board.community.free.service;

import com.example.dopamines.domain.board.community.free.model.entity.FreeBoard;
import com.example.dopamines.domain.board.community.free.model.entity.FreeLike;
import com.example.dopamines.domain.board.community.free.model.response.FreeLikeRes;
import com.example.dopamines.domain.board.community.free.repository.FreeBoardRepository;
import com.example.dopamines.domain.board.community.free.repository.FreeLikeRepository;
import com.example.dopamines.global.common.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.example.dopamines.global.common.BaseResponseStatus.COMMUNITY_BOARD_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class FreeLikeService {
    private final FreeLikeRepository freeLikeRepository;
    private final FreeBoardRepository freeBoardRepository;

//    public String create(User user, Long idx) {
//        Optional<FreeLike> result = freeLikeRepository.findByUserAndFreeBoard(user.getIdx(),idx);
//    }

    public FreeLikeRes delete(Long idx) {
        FreeBoard freeBoard = freeBoardRepository.findById(idx).orElseThrow(() -> new BaseException(COMMUNITY_BOARD_NOT_FOUND));
        FreeLike like = FreeLike.builder()
                .user(null)
                .freeBoard(freeBoard)
                .build();
        freeLikeRepository.save(like);

        return FreeLikeRes.builder()
                .idx(like.getIdx())
                .boardIdx(like.getFreeBoard().getIdx())
                .build();
    }
}
