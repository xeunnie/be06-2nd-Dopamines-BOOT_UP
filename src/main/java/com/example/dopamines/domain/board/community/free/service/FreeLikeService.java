package com.example.dopamines.domain.board.community.free.service;

import com.example.dopamines.domain.board.community.free.model.entity.FreeBoard;
import com.example.dopamines.domain.board.community.free.model.entity.FreeComment;
import com.example.dopamines.domain.board.community.free.model.entity.FreeCommentLike;
import com.example.dopamines.domain.board.community.free.model.entity.FreeLike;
import com.example.dopamines.domain.board.community.free.repository.FreeBoardRepository;
import com.example.dopamines.domain.board.community.free.repository.FreeCommentLikeRepository;
import com.example.dopamines.domain.board.community.free.repository.FreeCommentRepository;
import com.example.dopamines.domain.board.community.free.repository.FreeLikeRepository;
import com.example.dopamines.domain.user.model.entity.User;
import com.example.dopamines.global.common.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.example.dopamines.global.common.BaseResponseStatus.COMMUNITY_BOARD_NOT_FOUND;
import static com.example.dopamines.global.common.BaseResponseStatus.COMMUNITY_COMMENT_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class FreeLikeService {
    private final FreeLikeRepository freeLikeRepository;
    private final FreeBoardRepository freeBoardRepository;
    private final FreeCommentLikeRepository freeCommentLikeRepository;
    private final FreeCommentRepository freeCommentRepository;


    public String createFreeBoardLike(User user, Long idx) {
        Optional<FreeLike> result = freeLikeRepository.findByUserAndFreeBoard(user.getIdx(),idx);
        FreeLike freeLike;

        if(result.isPresent()){ // 이미 좋아요한 경우
            freeLike= result.get();
            freeLikeRepository.delete(freeLike);
            return "자유 게시글 좋아요 취소";
        }

        FreeBoard freeBoard = freeBoardRepository.findById(idx).orElseThrow(() -> new BaseException(COMMUNITY_BOARD_NOT_FOUND));
        freeLike = FreeLike.builder()
                .user(user)
                .freeBoard(freeBoard)
                .build();
        freeLikeRepository.save(freeLike);
        return "자유 게시글 좋아요 등록";
    }

    public String createCommentLike(User user, Long idx) {
        Optional<FreeCommentLike> result = freeCommentLikeRepository.findByUserAndIdx(user.getIdx(),idx);
        FreeCommentLike freeCommentLike;

        if(result.isPresent()){ // 이미 좋아요한 경우
            freeCommentLike= result.get();
            freeCommentLikeRepository.delete(freeCommentLike);
            return "자유 게시글 댓글 좋아요 취소";
        }

        FreeComment freeComment = freeCommentRepository.findById(idx).orElseThrow(() -> new BaseException(COMMUNITY_COMMENT_NOT_FOUND));
        freeCommentLike = FreeCommentLike.builder()
                .user(user)
                .freeComment(freeComment)
                .build();
        freeCommentLikeRepository.save(freeCommentLike);
        return "자유 게시글 댓글 좋아요 등록";
    }
}
