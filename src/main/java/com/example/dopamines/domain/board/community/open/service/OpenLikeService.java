package com.example.dopamines.domain.board.community.open.service;

import com.example.dopamines.domain.board.community.open.model.entity.*;
import com.example.dopamines.domain.board.community.open.repository.*;
import com.example.dopamines.domain.user.model.entity.User;
import com.example.dopamines.global.common.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.example.dopamines.global.common.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class OpenLikeService {
    private final OpenLikeRepository openLikeRepository;
    private final OpenBoardRepository openBoardRepository;
    private final OpenCommentLikeRepository openCommentLikeRepository;
    private final OpenCommentRepository openCommentRepository;
    private final OpenRecommentRepository openRecommentRepository;
    private final OpenRecommentLikeRepository openRecommentLikeRepository;


    public String createOpenBoardLike(User user, Long idx) {
        Optional<OpenLike> result = openLikeRepository.findByUserAndOpenBoard(user.getIdx(),idx);
        OpenLike openLike;

        if(result.isPresent()){ // 이미 좋아요한 경우
            openLike= result.get();
            openLikeRepository.delete(openLike);
            return "자유 게시글 좋아요 취소";
        }

        OpenBoard openBoard = openBoardRepository.findById(idx).orElseThrow(() -> new BaseException(COMMUNITY_BOARD_NOT_FOUND));
        openLike = OpenLike.builder()
                .user(user)
                .openBoard(openBoard)
                .build();
        openLikeRepository.save(openLike);
        return "자유 게시글 좋아요 등록";
    }

    public String createCommentLike(User user, Long idx) {
        Optional<OpenCommentLike> result = openCommentLikeRepository.findByUserAndIdx(user.getIdx(),idx);
        OpenCommentLike openCommentLike;

        if(result.isPresent()){ // 이미 좋아요한 경우
            openCommentLike= result.get();
            openCommentLikeRepository.delete(openCommentLike);
            return "자유 게시글 댓글 좋아요 취소";
        }

        OpenComment openComment = openCommentRepository.findById(idx).orElseThrow(() -> new BaseException(COMMUNITY_COMMENT_NOT_FOUND));
        openCommentLike = OpenCommentLike.builder()
                .user(user)
                .openComment(openComment)
                .build();
        openCommentLikeRepository.save(openCommentLike);
        return "자유 게시글 댓글 좋아요 등록";
    }

    public String createRecommentLike(User user, Long idx) {
        Optional<OpenRecommentLike> result = openRecommentLikeRepository.findByUserAndIdx(user.getIdx(),idx);
        OpenRecommentLike openRecommentLike;

        if(result.isPresent()){ // 이미 좋아요한 경우
            openRecommentLike= result.get();
            openRecommentLikeRepository.delete(openRecommentLike);
            return "자유 게시글 대댓글 좋아요 취소";
        }

        OpenRecomment openRecomment = openRecommentRepository.findById(idx).orElseThrow(() -> new BaseException(COMMUNITY_RECOMMENT_NOT_FOUND));
        openRecommentLike = OpenRecommentLike.builder()
                .user(user)
                .openRecomment(openRecomment)
                .build();
        openRecommentLikeRepository.save(openRecommentLike);
        return "자유 게시글 대댓글 좋아요 등록";
    }
}
