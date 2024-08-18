package com.example.dopamines.domain.board.community.free.service;

import com.example.dopamines.domain.board.community.free.model.entity.FreePost;
import com.example.dopamines.domain.board.community.free.model.entity.FreeComment;
import com.example.dopamines.domain.board.community.free.model.request.FreeCommentReq;
import com.example.dopamines.domain.board.community.free.model.request.FreeCommentUpdateReq;
import com.example.dopamines.domain.board.community.free.model.response.FreeCommentReadRes;
import com.example.dopamines.domain.board.community.free.model.response.FreeRecommentReadRes;
import com.example.dopamines.domain.board.community.free.repository.FreePostRepository;

import com.example.dopamines.domain.board.community.free.repository.FreeCommentRepository;
import com.example.dopamines.domain.board.community.free.repository.FreeRecommentRepository;
import com.example.dopamines.domain.user.model.entity.User;
import com.example.dopamines.global.common.BaseException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import com.example.dopamines.global.common.BaseResponseStatus;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static com.example.dopamines.global.common.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class FreeCommentService {
    private final FreeCommentRepository freeCommentRepository;
    private final FreePostRepository freePostRepository;
    private final FreeRecommentRepository freeRecommentRepository;

    @Transactional
    public FreeCommentReadRes create(User user, FreeCommentReq req) {
        FreePost freePost = freePostRepository.findById(req.getFreePostIdx()).orElseThrow(()->new BaseException(BaseResponseStatus.COMMUNITY_BOARD_NOT_FOUND));

        if(req.getContent() == null){
            throw new BaseException( BaseResponseStatus.COMMUNITY_CONTENT_NOT_FOUND);
        }

        FreeComment freeComment = freeCommentRepository.save(FreeComment.builder()
                .freePost(freePost)
                .content(req.getContent())
                .user(user)
                .createdAt(LocalDateTime.now())
                .build()
        );

//        private Long idx;
//        private Long freePostIdx;
//        private String content;
//        private String author;
//        private LocalDateTime createdAt;
//        private Integer likeCount;
//        private List<FreeRecommentReadRes> recommentList;
        return FreeCommentReadRes.builder()
                .idx(freeComment.getIdx())
                .freePostIdx(freeComment.getFreePost().getIdx())
                .content(freeComment.getContent())
                .author(freeComment.getUser().getNickname())
                .createdAt(freeComment.getCreatedAt())
                .likeCount(freeComment.getLikesCount())
                .build();
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
        FreeComment freeComment = freeCommentRepository.findById(req.getIdx()).orElseThrow(()-> new BaseException(BaseResponseStatus.COMMUNITY_COMMENT_NOT_FOUND));
        FreePost freePost = freePostRepository.findById(freeComment.getFreePost().getIdx()).orElseThrow(() -> new BaseException(BaseResponseStatus.COMMUNITY_BOARD_NOT_FOUND));

        if (!(freeComment.getUser().getIdx()).equals(user.getIdx())){
            throw  new BaseException(BaseResponseStatus.COMMUNITY_USER_NOT_AUTHOR);
        }
        else{
            freeComment.setContent(req.getContent());
            freeComment.setCreatedAt(LocalDateTime.now());

            freeCommentRepository.save(freeComment);
            return "댓글 수정 완료";
        }
    }


    public String delete(User user, Long idx) {
        FreeComment freeComment = freeCommentRepository.findById(idx).orElseThrow(()-> new BaseException(BaseResponseStatus.COMMUNITY_COMMENT_NOT_FOUND));
        FreePost freePost = freePostRepository.findById(freeComment.getFreePost().getIdx()).orElseThrow(() -> new BaseException(BaseResponseStatus.COMMUNITY_BOARD_NOT_FOUND));

        if (!(freeComment.getUser().getIdx()).equals(user.getIdx())){
            throw new BaseException(BaseResponseStatus.COMMUNITY_USER_NOT_AUTHOR);
        }
        else{
            freeCommentRepository.delete(freeComment);
            return "댓글 삭제 완료";
        }
    }

    public List<FreeCommentReadRes> findAllWithPage(Long postIdx, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "idx"));
        Slice<FreeComment> comments = freeCommentRepository.findAllWithPaging(pageable, postIdx);

        List<FreeCommentReadRes> res = comments.stream()
                .map((comment) -> {
                    // FreeRecomment를 FreeRecommentReadRes로 변환
                    List<FreeRecommentReadRes> recommentList = comment.getFreeRecomments().stream()
                            .map(recomment -> FreeRecommentReadRes.builder()
                                    .idx(recomment.getIdx())
                                    .content(recomment.getContent())
                                    .author(recomment.getUser().getNickname())
                                    .createdAt(recomment.getCreatedAt())
                                    .commentIdx(recomment.getFreeComment().getIdx())
                                    .likeCount(recomment.getLikesCount())
                                    .build())
                            .collect(Collectors.toList());

                    // FreeCommentReadRes 빌더에 변환된 recommentList를 사용
                    return FreeCommentReadRes.builder()
                            .idx(comment.getIdx())
                            .author(comment.getUser().getNickname())
                            .content(comment.getContent())
                            .createdAt(comment.getCreatedAt())
                            .likeCount(comment.getLikesCount())
                            .recommentList(recommentList)
                            .build();
                })
                .collect(Collectors.toList());

        return res;
    }
}