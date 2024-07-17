package com.example.dopamines.domain.board.community.free.service;

import com.example.dopamines.domain.board.community.free.model.entity.FreeBoard;
import com.example.dopamines.domain.board.community.free.model.entity.FreeComment;
import com.example.dopamines.domain.board.community.free.model.request.FreeCommentReq;
import com.example.dopamines.domain.board.community.free.model.response.FreeBoardReadRes;
import com.example.dopamines.domain.board.community.free.model.response.FreeCommentReadRes;
import com.example.dopamines.domain.board.community.free.repository.FreeBoardRepository;
import com.example.dopamines.domain.board.community.free.repository.FreeCommentRepository;
import com.example.dopamines.domain.user.model.entity.User;
import com.example.dopamines.global.common.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.dopamines.global.common.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class FreeCommentService {
    private final FreeCommentRepository freeCommentRepository;
    private final FreeBoardRepository freeBoardRepository;

    @Transactional
    public String create(User user, FreeCommentReq req) {
        FreeBoard freeBoard = freeBoardRepository.findById(req.getIdx()).orElseThrow(()->new BaseException(COMMUNITY_BOARD_NOT_FOUND));

        if(req.getContent() == null){
            throw new BaseException(COMMUNITY_CONTENT_NOT_FOUND);
        }

        freeCommentRepository.save(FreeComment.builder()
                .freeBoard(freeBoard)
                .content(req.getContent())
                .user(user)
                .createdAt(LocalDateTime.now())
                .build()
        );

        return "자유 게시판 댓글 등록";
    }

    // TODO : 댓글 좋아요까지 구현 후 성능 테스트
    public List<FreeCommentReadRes> read(User user,Long idx) {
        FreeBoard freeBoard = freeBoardRepository.findById(idx).orElseThrow(() -> new BaseException(COMMUNITY_BOARD_NOT_FOUND));
        List<FreeCommentReadRes> freeCommentReadResList = new ArrayList<>();
        for(FreeComment freeComment : freeBoard.getComments()){
            freeCommentReadResList.add(FreeCommentReadRes.builder()
                    .idx(freeComment.getIdx())
                    .freeBoardIdx(freeBoard.getIdx())
                    .content(freeComment.getContent())
                    .author(user.getNickname())
                    .createdAt(freeComment.getCreatedAt())
                    .likeCount(freeComment.getLikes().size())
                    .build());

        }
        return freeCommentReadResList;
    }

    public String update(User user, FreeCommentReq req) {
        FreeBoard freeBoard = freeBoardRepository.findById(req.getIdx()).orElseThrow(()-> new BaseException(COMMUNITY_BOARD_NOT_FOUND));
        if(freeBoard.getUser().getIdx()!= user.getIdx()){
            throw new BaseException(COMMUNITY_USER_NOT_AUTHOR);
        }

    }
}
