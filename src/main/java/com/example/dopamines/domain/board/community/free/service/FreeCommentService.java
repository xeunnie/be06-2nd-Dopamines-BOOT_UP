package com.example.dopamines.domain.board.community.free.service;

import com.example.dopamines.domain.board.community.free.model.entity.FreeBoard;
import com.example.dopamines.domain.board.community.free.model.entity.FreeComment;
import com.example.dopamines.domain.board.community.free.model.request.FreeCommentReq;
import com.example.dopamines.domain.board.community.free.model.request.FreeCommentUpdateReq;
import com.example.dopamines.domain.board.community.free.repository.FreeBoardRepository;
import com.example.dopamines.domain.board.community.free.repository.FreeCommentRepository;
import com.example.dopamines.domain.board.community.free.repository.FreeRecommentRepository;
import com.example.dopamines.domain.user.model.entity.User;
import com.example.dopamines.global.common.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.example.dopamines.global.common.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class FreeCommentService {
    private final FreeCommentRepository freeCommentRepository;
    private final FreeBoardRepository freeBoardRepository;
    private final FreeRecommentRepository freeRecommentRepository;

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

    // TODO : 내가 쓴 댓글, 대댓글 조회
    // TODO :  대댓글 조회까지 한번에 처리
    // TODO : 댓글 좋아요까지 구현 후 성능 테스트
//    public List<FreeCommentReadRes> read(User user) {
//        FreeBoard freeBoard = freeBoardRepository.findById(idx).orElseThrow(() -> new BaseException(COMMUNITY_BOARD_NOT_FOUND));
//        List<FreeCommentReadRes> freeCommentReadResList = new ArrayList<>();
//        for(FreeComment freeComment : freeBoard.getComments()){
//            freeCommentReadResList.add(FreeCommentReadRes.builder()
//                    .idx(freeComment.getIdx())
//                    .freeBoardIdx(freeBoard.getIdx())
//                    .content(freeComment.getContent())
//                    .author(user.getNickname())
//                    .createdAt(freeComment.getCreatedAt())
//                    .likeCount(freeComment.getLikes().size())
//                    .build());
//
//        }
//        return freeCommentReadResList;
//    }

    public String update(User user, FreeCommentUpdateReq req) {
        FreeComment freeComment = freeCommentRepository.findById(req.getIdx()).orElseThrow(()-> new BaseException(COMMUNITY_COMMENT_NOT_FOUND));
        FreeBoard freeBoard = freeBoardRepository.findById(freeComment.getFreeBoard().getIdx()).orElseThrow(() -> new BaseException(COMMUNITY_BOARD_NOT_FOUND));

        if (freeComment.getUser().getIdx() != user.getIdx()){
            throw  new BaseException(COMMUNITY_USER_NOT_AUTHOR);
        }
        else{
            freeComment.setContent(req.getContent());
            freeComment.setCreatedAt(LocalDateTime.now());

            freeCommentRepository.save(freeComment);
            return "댓글 수정 완료";
        }
    }


    public String delete(User user, Long idx) {
        FreeComment freeComment = freeCommentRepository.findById(idx).orElseThrow(()-> new BaseException(COMMUNITY_COMMENT_NOT_FOUND));
        FreeBoard freeBoard = freeBoardRepository.findById(freeComment.getFreeBoard().getIdx()).orElseThrow(() -> new BaseException(COMMUNITY_BOARD_NOT_FOUND));

        if (freeComment.getUser().getIdx() != user.getIdx()){
            throw  new BaseException(COMMUNITY_USER_NOT_AUTHOR);
        }
        else{
            freeCommentRepository.delete(freeComment);
            return "댓글 삭제 완료";
        }
    }
}