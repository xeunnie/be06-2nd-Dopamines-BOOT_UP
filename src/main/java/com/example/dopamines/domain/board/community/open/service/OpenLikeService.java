package com.example.dopamines.domain.board.community.open.service;

import com.example.dopamines.domain.board.community.open.model.entity.*;
import com.example.dopamines.domain.board.community.open.repository.*;
import com.example.dopamines.domain.user.model.entity.User;
import com.example.dopamines.global.common.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.example.dopamines.global.common.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class OpenLikeService {
    private final OpenPostLikeRepository openPostLikeRepository;
    private final OpenPostRepository openPostRepository;
    private final OpenCommentLikeRepository openCommentLikeRepository;
    private final OpenCommentRepository openCommentRepository;
    private final OpenRecommentRepository openRecommentRepository;
    private final OpenRecommentLikeRepository openRecommentLikeRepository;


    @Transactional
    public String createOpenPostLike(User user, Long idx) {
        OpenPost openPost = openPostRepository.findById(idx).orElseThrow(() -> new BaseException(COMMUNITY_BOARD_NOT_FOUND));

        Optional<OpenPostLike> result = openPostLikeRepository.findByUserAndOpenPost(user.getIdx(), idx);
        // .orElseThrow(() -> new BaseException(BaseResponseStatus.COMMUNITY_POST_LIKE_FAILED));
        OpenPostLike openPostLike = null;

        if(result.isPresent()){ // 이미 좋아요한 경우
            openPostLike = result.get();
            openPostLikeRepository.delete(openPostLike);

            openPost.subLikesCount();
            openPostRepository.save(openPost);

            return "자유 게시글 좋아요 취소";
        }


        openPost.addLikesCount();
        openPostRepository.save(openPost);

        openPostLike = OpenPostLike.builder()
                .user(user)
                .openPost(openPost)
                .build();
        openPostLikeRepository.save(openPostLike);
        return "자유 게시글 좋아요 등록";
    }

    @Transactional
    public String createCommentLike(User user, Long idx) {
        OpenComment openComment = openCommentRepository.findById(idx).orElseThrow(() -> new BaseException(COMMUNITY_COMMENT_NOT_FOUND));

        Optional<OpenCommentLike> result = openCommentLikeRepository.findByUserAndIdx(user.getIdx(),idx);
        OpenCommentLike openCommentLike = null;

        if(result.isPresent()){ // 이미 좋아요한 경우
            openCommentLike = result.get();
            openCommentLikeRepository.delete(openCommentLike);

            openComment.subLikesCount();
            openCommentRepository.save(openComment);

            return "자유 게시글 댓글 좋아요 취소";
        }

        openComment.addLikesCount();
        openCommentRepository.save(openComment);


        openCommentLike = OpenCommentLike.builder()
                .user(user)
                .openComment(openComment)
                .build();
        openCommentLikeRepository.save(openCommentLike);
        return "자유 게시글 댓글 좋아요 등록";
    }

    @Transactional
    public String createRecommentLike(User user, Long idx) {
        OpenRecomment openRecomment = openRecommentRepository.findById(idx).orElseThrow(() -> new BaseException(COMMUNITY_RECOMMENT_NOT_FOUND));

        Optional<OpenRecommentLike> result = openRecommentLikeRepository.findByUserAndIdx(user.getIdx(),idx);
        OpenRecommentLike openRecommentLike = null;

        if(result.isPresent()){ // 이미 좋아요한 경우
            openRecommentLike = result.get();
            openRecommentLikeRepository.delete(openRecommentLike);

            openRecomment.subLikesCount();
            openRecommentRepository.save(openRecomment);

            return "자유 게시글 대댓글 좋아요 취소";
        }

        openRecomment.addLikesCount();
        openRecommentRepository.save(openRecomment);


        openRecommentLike = OpenRecommentLike.builder()
                .user(user)
                .openRecomment(openRecomment)
                .build();
        openRecommentLikeRepository.save(openRecommentLike);
        return "자유 게시글 대댓글 좋아요 등록";
    }
}
