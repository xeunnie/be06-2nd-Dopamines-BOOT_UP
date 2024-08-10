package com.example.dopamines.domain.board.community.open.service;

import com.example.dopamines.domain.board.community.open.model.entity.OpenComment;
import com.example.dopamines.domain.board.community.open.model.entity.OpenPost;
import com.example.dopamines.domain.board.community.open.model.request.OpenCommentReq;
import com.example.dopamines.domain.board.community.open.model.request.OpenCommentUpdateReq;
import com.example.dopamines.domain.board.community.open.model.response.OpenCommentReadRes;
import com.example.dopamines.domain.board.community.open.repository.OpenCommentRepository;
import com.example.dopamines.domain.board.community.open.repository.OpenPostRepository;
import com.example.dopamines.domain.board.community.open.repository.OpenRecommentRepository;
import com.example.dopamines.domain.user.model.entity.User;
import com.example.dopamines.global.common.BaseException;
import com.example.dopamines.global.common.BaseResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OpenCommentService {
    private final OpenCommentRepository openCommentRepository;
    private final OpenPostRepository openPostRepository;
    private final OpenRecommentRepository openRecommentRepository;

    @Transactional
    public String create(User user, OpenCommentReq req) {
        OpenPost openPost = openPostRepository.findById(req.getIdx()).orElseThrow(()->new BaseException(BaseResponseStatus.COMMUNITY_BOARD_NOT_FOUND));

        if(req.getContent() == null){
            throw new BaseException( BaseResponseStatus.COMMUNITY_CONTENT_NOT_FOUND);
        }

        openCommentRepository.save(OpenComment.builder()
                .openPost(openPost)
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
//    public List<OpenCommentReadRes> read(User user) {
//        OpenBoard openBoard = openBoardRepository.findById(idx).orElseThrow(() -> new BaseException(COMMUNITY_BOARD_NOT_FOUND));
//        List<OpenCommentReadRes> openCommentReadResList = new ArrayList<>();
//        for(OpenComment openComment : openBoard.getComments()){
//            openCommentReadResList.add(OpenCommentReadRes.builder()
//                    .idx(openComment.getIdx())
//                    .openBoardIdx(openBoard.getIdx())
//                    .content(openComment.getContent())
//                    .author(user.getNickname())
//                    .createdAt(openComment.getCreatedAt())
//                    .likeCount(openComment.getLikes().size())
//                    .build());
//
//        }
//        return openCommentReadResList;
//    }

    public String update(User user, OpenCommentUpdateReq req) {
        OpenComment openComment = openCommentRepository.findById(req.getIdx()).orElseThrow(()-> new BaseException(BaseResponseStatus.COMMUNITY_COMMENT_NOT_FOUND));
        OpenPost openPost = openPostRepository.findById(openComment.getOpenPost().getIdx()).orElseThrow(() -> new BaseException(BaseResponseStatus.COMMUNITY_BOARD_NOT_FOUND));

        if (!(openComment.getUser().getIdx()).equals(user.getIdx())){
            throw  new BaseException(BaseResponseStatus.COMMUNITY_USER_NOT_AUTHOR);
        }
        else{
            openComment.setContent(req.getContent());
            openComment.setCreatedAt(LocalDateTime.now());

            openCommentRepository.save(openComment);
            return "댓글 수정 완료";
        }
    }


    public String delete(User user, Long idx) {
        OpenComment openComment = openCommentRepository.findById(idx).orElseThrow(()-> new BaseException(BaseResponseStatus.COMMUNITY_COMMENT_NOT_FOUND));
        OpenPost openPost = openPostRepository.findById(openComment.getOpenPost().getIdx()).orElseThrow(() -> new BaseException(BaseResponseStatus.COMMUNITY_BOARD_NOT_FOUND));

        if (!(openComment.getUser().getIdx()).equals(user.getIdx())){
            throw  new BaseException(BaseResponseStatus.COMMUNITY_USER_NOT_AUTHOR);
        }
        else{
            openCommentRepository.delete(openComment);
            return "댓글 삭제 완료";
        }
    }

    public List<OpenCommentReadRes> findAllWithPage(Long postIdx, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "idx"));
        Slice<OpenComment> comments = openCommentRepository.findAllWithPaging(pageable, postIdx);

        List<OpenCommentReadRes> res = comments.stream()
                .map((comment) -> OpenCommentReadRes.builder()
                        .idx(comment.getIdx())
                        .author(comment.getUser().getNickname())
                        .content(comment.getContent())
                        .createdAt(comment.getCreatedAt())
                        .likeCount(comment.getLikesCount())
                        .build()
                ).collect(Collectors.toList());

        return res;
    }
}