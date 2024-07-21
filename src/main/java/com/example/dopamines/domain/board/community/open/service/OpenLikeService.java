package com.example.dopamines.domain.board.community.open.service;

import com.example.dopamines.domain.board.community.open.model.entity.*;
import com.example.dopamines.domain.board.community.open.repository.*;
import com.example.dopamines.domain.user.model.entity.User;
import com.example.dopamines.global.common.BaseException;
import com.example.dopamines.global.common.BaseResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OpenLikeService {
    private final OpenPostLikeRepository openPostLikeRepository;
    private final OpenPostRepository openPostRepository;
    private final OpenCommentLikeRepository openCommentLikeRepository;
    private final OpenCommentRepository openCommentRepository;
    private final OpenRecommentRepository openRecommentRepository;
    private final OpenRecommentLikeRepository openRecommentLikeRepository;


    public String createOpenPostLike(User user, Long idx) {
        OpenPostLike result = openPostLikeRepository.findByUserAndOpenPost(user.getIdx(), idx)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.COMMUNITY_POST_LIKE_FAILED));
        OpenPostLike openPostLike;

        if(result != null){ // 이미 좋아요한 경우
            openPostLikeRepository.delete(result);
            return "자유 게시글 좋아요 취소";
        }

        OpenPost openPost = openPostRepository.findById(idx).orElseThrow(() -> new BaseException(BaseResponseStatus.COMMUNITY_BOARD_NOT_FOUND));
        openPostLike = OpenPostLike.builder()
                .user(user)
                .openPost(openPost)
                .build();
        openPostLikeRepository.save(openPostLike);
        return "자유 게시글 좋아요 등록";
    }

    public String createCommentLike(User user, Long idx) {
        OpenCommentLike result = openCommentLikeRepository.findByUserAndIdx(user.getIdx(),idx)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.COMMUNITY_COMMENT_LIKE_FAILED));
        OpenCommentLike openCommentLike;

        if(result != null){ // 이미 좋아요한 경우
            openCommentLikeRepository.delete(result);
            return "자유 게시글 댓글 좋아요 취소";
        }

        OpenComment openComment = openCommentRepository.findById(idx).orElseThrow(() -> new BaseException(BaseResponseStatus.COMMUNITY_COMMENT_NOT_FOUND));
        openCommentLike = OpenCommentLike.builder()
                .user(user)
                .openComment(openComment)
                .build();
        openCommentLikeRepository.save(openCommentLike);
        return "자유 게시글 댓글 좋아요 등록";
    }

    public String createRecommentLike(User user, Long idx) {
        OpenRecommentLike result = openRecommentLikeRepository.findByUserAndIdx(user.getIdx(),idx)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.COMMUNITY_COMMENT_LIKE_FAILED));
        OpenRecommentLike openRecommentLike;

        if(result != null){ // 이미 좋아요한 경우
            openRecommentLikeRepository.delete(result);
            return "자유 게시글 대댓글 좋아요 취소";
        }

        OpenRecomment openRecomment = openRecommentRepository.findById(idx).orElseThrow(() -> new BaseException(BaseResponseStatus.COMMUNITY_RECOMMENT_NOT_FOUND));
        openRecommentLike = OpenRecommentLike.builder()
                .user(user)
                .openRecomment(openRecomment)
                .build();
        openRecommentLikeRepository.save(openRecommentLike);
        return "자유 게시글 대댓글 좋아요 등록";
    }
}
