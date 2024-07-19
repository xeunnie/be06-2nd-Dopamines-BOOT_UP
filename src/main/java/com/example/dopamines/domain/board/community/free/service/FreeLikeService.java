package com.example.dopamines.domain.board.community.free.service;

import com.example.dopamines.domain.board.community.free.model.entity.*;
import com.example.dopamines.domain.board.community.free.repository.*;
import com.example.dopamines.domain.user.model.entity.User;
import com.example.dopamines.global.common.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;

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


    @Transactional
    public String createFreePostLike(User user, Long idx) {
        FreePost freePost = freePostRepository.findById(idx).orElseThrow(() -> new BaseException(COMMUNITY_BOARD_NOT_FOUND));

        Optional<FreePostLike> result = freePostLikeRepository.findByUserAndFreePost(user.getIdx(),idx);
        FreePostLike freePostLike;

        if(result.isPresent()){ // 이미 좋아요한 경우
            freePostLike = result.get();
            freePostLikeRepository.delete(freePostLike);

            freePost.subLikesCount();
            freePostRepository.save(freePost);

            return "자유 게시글 좋아요 취소";
        }


        freePost.addLikesCount();
        freePostRepository.save(freePost);


        freePostLike = FreePostLike.builder()
                .user(user)
                .freePost(freePost)
                .build();
        freePostLikeRepository.save(freePostLike);
        return "자유 게시글 좋아요 등록";
    }

    @Transactional
    public String createCommentLike(User user, Long idx) {
        FreeComment freeComment = freeCommentRepository.findById(idx).orElseThrow(() -> new BaseException(COMMUNITY_COMMENT_NOT_FOUND));

        Optional<FreeCommentLike> result = freeCommentLikeRepository.findByUserAndIdx(user.getIdx(),idx);
        FreeCommentLike freeCommentLike;

        if(result.isPresent()){ // 이미 좋아요한 경우
            freeCommentLike= result.get();
            freeCommentLikeRepository.delete(freeCommentLike);

            freeComment.subLikesCount();
            freeCommentRepository.save(freeComment);

            return "자유 게시글 댓글 좋아요 취소";
        }

        freeComment.addLikesCount();
        freeCommentRepository.save(freeComment);

        freeCommentLike = FreeCommentLike.builder()
                .user(user)
                .freeComment(freeComment)
                .build();
        freeCommentLikeRepository.save(freeCommentLike);
        return "자유 게시글 댓글 좋아요 등록";
    }

    @Transactional
    public String createRecommentLike(User user, Long idx) {
        FreeRecomment freeRecomment = freeRecommentRepository.findById(idx).orElseThrow(() -> new BaseException(COMMUNITY_RECOMMENT_NOT_FOUND));

        Optional<FreeRecommentLike> result = freeRecommentLikeRepository.findByUserAndIdx(user.getIdx(),idx);
        FreeRecommentLike freeRecommentLike;

        if(result.isPresent()){ // 이미 좋아요한 경우
            freeRecommentLike= result.get();
            freeRecommentLikeRepository.delete(freeRecommentLike);

            freeRecomment.subLikesCount();
            freeRecommentRepository.save(freeRecomment);

            return "자유 게시글 대댓글 좋아요 취소";
        }

        freeRecomment.addLikesCount();
        freeRecommentRepository.save(freeRecomment);

        freeRecommentLike = FreeRecommentLike.builder()
                .user(user)
                .freeRecomment(freeRecomment)
                .build();
        freeRecommentLikeRepository.save(freeRecommentLike);
        return "자유 게시글 대댓글 좋아요 등록";
    }
}
