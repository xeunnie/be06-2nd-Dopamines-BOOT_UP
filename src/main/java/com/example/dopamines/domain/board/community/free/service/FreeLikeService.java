package com.example.dopamines.domain.board.community.free.service;

import com.example.dopamines.domain.board.community.free.model.entity.*;
import com.example.dopamines.domain.board.community.free.repository.*;
import com.example.dopamines.domain.user.model.entity.User;
import com.example.dopamines.global.common.BaseException;
import com.example.dopamines.global.common.BaseResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.example.dopamines.global.common.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class FreeLikeService {
    private final FreePostLikeRepository freePostLikeRepository;
    private final FreePostRepository freePostRepository;
    private final FreeCommentLikeRepository freeCommentLikeRepository;
    private final FreeCommentRepository freeCommentRepository;
    private final FreeRecommentRepository freeRecommentRepository;
    private final FreeRecommentLikeRepository freeRecommentLikeRepository;


    public String createFreePostLike(User user, Long idx) {
        FreePostLike result = freePostLikeRepository.findByUserAndFreePost(user.getIdx(), idx)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.COMMUNITY_POST_LIKE_FAILED));
        FreePostLike freePostLike;

        if(result != null){ // 이미 좋아요한 경우
            freePostLikeRepository.delete(result);
            return "자유 게시글 좋아요 취소";
        }

        FreePost freePost = freePostRepository.findById(idx).orElseThrow(() -> new BaseException(BaseResponseStatus.COMMUNITY_BOARD_NOT_FOUND));
        freePostLike = FreePostLike.builder()
                .user(user)
                .freePost(freePost)
                .build();
        freePostLikeRepository.save(freePostLike);
        return "자유 게시글 좋아요 등록";
    }

    public String createCommentLike(User user, Long idx) {
        FreeCommentLike result = freeCommentLikeRepository.findByUserAndIdx(user.getIdx(),idx)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.COMMUNITY_COMMENT_LIKE_FAILED));
        FreeCommentLike freeCommentLike;

        if(result != null){ // 이미 좋아요한 경우
            freeCommentLikeRepository.delete(result);
            return "자유 게시글 댓글 좋아요 취소";
        }

        FreeComment freeComment = freeCommentRepository.findById(idx).orElseThrow(() -> new BaseException(BaseResponseStatus.COMMUNITY_COMMENT_NOT_FOUND));
        freeCommentLike = FreeCommentLike.builder()
                .user(user)
                .freeComment(freeComment)
                .build();
        freeCommentLikeRepository.save(freeCommentLike);
        return "자유 게시글 댓글 좋아요 등록";
    }

    public String createRecommentLike(User user, Long idx) {
        FreeRecommentLike result = freeRecommentLikeRepository.findByUserAndIdx(user.getIdx(),idx)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.COMMUNITY_COMMENT_LIKE_FAILED));
        FreeRecommentLike freeRecommentLike;

        if(result != null){ // 이미 좋아요한 경우
            freeRecommentLikeRepository.delete(result);
            return "자유 게시글 대댓글 좋아요 취소";
        }

        FreeRecomment freeRecomment = freeRecommentRepository.findById(idx).orElseThrow(() -> new BaseException(BaseResponseStatus.COMMUNITY_RECOMMENT_NOT_FOUND));
        freeRecommentLike = FreeRecommentLike.builder()
                .user(user)
                .freeRecomment(freeRecomment)
                .build();
        freeRecommentLikeRepository.save(freeRecommentLike);
        return "자유 게시글 대댓글 좋아요 등록";
    }
}
