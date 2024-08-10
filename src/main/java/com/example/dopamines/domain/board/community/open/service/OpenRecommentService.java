package com.example.dopamines.domain.board.community.open.service;

import com.example.dopamines.domain.board.community.open.model.entity.OpenComment;
import com.example.dopamines.domain.board.community.open.model.entity.OpenPost;
import com.example.dopamines.domain.board.community.open.model.entity.OpenRecomment;
import com.example.dopamines.domain.board.community.open.model.request.OpenRecommentReq;
import com.example.dopamines.domain.board.community.open.model.request.OpenRecommentUpdateReq;
import com.example.dopamines.domain.board.community.open.model.response.OpenRecommentReadRes;
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

import static com.example.dopamines.global.common.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class OpenRecommentService {
    private final OpenCommentRepository openCommentRepository;
    private final OpenPostRepository openPostRepository;
    private final OpenRecommentRepository openRecommentRepository;


    @Transactional
    public String create(User user, OpenRecommentReq req) {
        OpenComment openComment = openCommentRepository.findById(req.getCommentIdx()).orElseThrow(()-> new BaseException(BaseResponseStatus.COMMUNITY_COMMENT_NOT_FOUND));
        OpenPost openPost = openPostRepository.findById(openComment.getOpenPost().getIdx()).orElseThrow(() -> new BaseException(BaseResponseStatus.COMMUNITY_BOARD_NOT_FOUND));

        if(req.getContent() == null){
            throw new BaseException(BaseResponseStatus.COMMUNITY_CONTENT_NOT_FOUND);
        }

        openRecommentRepository.save(OpenRecomment.builder()
                .openComment(openComment)
                .content(req.getContent())
                .user(user)
                .createdAt(LocalDateTime.now())
                .build()
        );

        return "자유 게시판 대댓글 등록";
    }

    public String update(User user, OpenRecommentUpdateReq req) {
        OpenRecomment openRecomment = openRecommentRepository.findById(req.getIdx()).orElseThrow(()-> new BaseException(BaseResponseStatus.COMMUNITY_RECOMMENT_NOT_FOUND));
        OpenComment openComment = openCommentRepository.findById(openRecomment.getOpenComment().getIdx()).orElseThrow(()-> new BaseException(BaseResponseStatus.COMMUNITY_COMMENT_NOT_FOUND));
        OpenPost openPost = openPostRepository.findById(openComment.getOpenPost().getIdx()).orElseThrow(() -> new BaseException(BaseResponseStatus.COMMUNITY_BOARD_NOT_FOUND));

        if (!(openRecomment.getUser().getIdx()).equals(user.getIdx())){
            throw  new BaseException(COMMUNITY_USER_NOT_AUTHOR);
        }
        else{
            openRecomment.setContent(req.getContent());
            openRecomment.setCreatedAt(LocalDateTime.now());

            openRecommentRepository.save(openRecomment);
            return "대댓글 수정 완료";
        }
    }

    public String delete(User user, Long idx) {
        OpenRecomment openRecomment = openRecommentRepository.findById(idx).orElseThrow(()-> new BaseException(BaseResponseStatus.COMMUNITY_RECOMMENT_NOT_FOUND));

        if (!(openRecomment.getUser().getIdx()).equals(user.getIdx())){
            throw  new BaseException(BaseResponseStatus.COMMUNITY_USER_NOT_AUTHOR);
        }
        else{
            openRecommentRepository.delete(openRecomment);
            return "대댓글 삭제 완료";
        }
    }

    public List<OpenRecommentReadRes> findAllWithPage(Long commentIdx, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "idx"));
        Slice<OpenRecomment> comments = openRecommentRepository.findAllWithPaging(pageable, commentIdx);

        List<OpenRecommentReadRes> res = comments.stream()
                .map((comment) -> OpenRecommentReadRes.builder()
                        .idx(comment.getIdx())
                        .author(comment.getUser().getNickname())
                        .content(comment.getContent())
                        .createdAt(comment.getCreatedAt())
                        .likeCount(comment.getLikesCount())
                        .openPostIdx(comment.getOpenComment().getOpenPost().getIdx())
                        .commentIdx(comment.getIdx())
                        .build()
                ).collect(Collectors.toList());

        return res;
    }
}
