package com.example.dopamines.domain.board.community.open.service;

import com.example.dopamines.domain.board.community.open.model.entity.OpenBoard;
import com.example.dopamines.domain.board.community.open.model.entity.OpenComment;
import com.example.dopamines.domain.board.community.open.model.entity.OpenRecomment;
import com.example.dopamines.domain.board.community.open.model.request.OpenBoardReq;
import com.example.dopamines.domain.board.community.open.model.request.OpenBoardUpdateReq;
import com.example.dopamines.domain.board.community.open.model.response.OpenBoardReadRes;
import com.example.dopamines.domain.board.community.open.model.response.OpenBoardRes;
import com.example.dopamines.domain.board.community.open.model.response.OpenCommentReadRes;
import com.example.dopamines.domain.board.community.open.model.response.OpenRecommentReadRes;
import com.example.dopamines.domain.board.community.open.repository.OpenBoardRepository;
import com.example.dopamines.domain.user.model.entity.User;
import com.example.dopamines.global.common.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.example.dopamines.global.common.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class OpenBoardService {
    private final OpenBoardRepository openBoardRepository;

    @Transactional
    public String create(User user, OpenBoardReq req) {

        if(req.getTitle() == null){
            throw new BaseException(COMMUNITY_TITLE_NOT_FOUND);
        }
        if(req.getContent() == null){
            throw new BaseException(COMMUNITY_CONTENT_NOT_FOUND);
        }
        OpenBoard openBoard = openBoardRepository.save(OpenBoard.builder()
                .title(req.getTitle())
                .content(req.getContent())
                .user(user)
                .image(req.getImage())
                .createdAt(LocalDateTime.now())
                .build()
        );

        return "자유 게시판 게시글 등록";
    }

    public OpenBoardReadRes read(Long idx) {
        OpenBoard openBoard = openBoardRepository.findById(idx).orElseThrow(() -> new BaseException(COMMUNITY_BOARD_NOT_FOUND));

        List<OpenCommentReadRes> openCommentReadResList = new ArrayList<>();
        for(OpenComment openComment : openBoard.getComments()){
            List<OpenRecommentReadRes> openRecommentReadResList = new ArrayList<>();
            for(OpenRecomment openRecomment : openComment.getOpenRecomments()){
                openRecommentReadResList.add(OpenRecommentReadRes.builder()
                        .idx(openRecomment.getIdx())
                        .openBoardIdx(openBoard.getIdx())
                        .commentIdx(openRecomment.getOpenComment().getIdx())
                        .content(openRecomment.getContent())
                        .author(openRecomment.getUser().getNickname())
                        .createdAt(openRecomment.getCreatedAt())
                        .likeCount(openRecomment.getLikes().size())
                        .build());
            }
            openCommentReadResList.add(OpenCommentReadRes.builder()
                    .idx(openComment.getIdx())
                    .openBoardIdx(openBoard.getIdx())
                    .content(openComment.getContent())
                    .author(openComment.getUser().getNickname())
                    .createdAt(openComment.getCreatedAt())
                    .likeCount(openComment.getLikes().size())
                    .recommentList(openRecommentReadResList)
                    .build());

        }

        return OpenBoardReadRes.builder()
                .idx(openBoard.getIdx())
                .title(openBoard.getTitle())
                .content(openBoard.getContent())
                .author(openBoard.getUser().getNickname())
                .image(openBoard.getImage())
                .created_at(LocalDateTime.now())
                .likeCount(openBoard.getLikes().size())
                .openCommentList(openCommentReadResList)
                .build();
    }

    public List<OpenBoardRes> readAll(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "idx"));
        Slice<OpenBoard> result = openBoardRepository.findAllWithPaging(pageable);
        List<OpenBoardRes> openBoardResList = new ArrayList<>();

        for(OpenBoard openBoard : result.getContent()){
            openBoardResList.add(OpenBoardRes.builder()
                    .idx(openBoard.getIdx())
                    .title(openBoard.getTitle())
                    .content(openBoard.getContent())
                    .build());
        }
        return openBoardResList;
    }

    public OpenBoardRes update(User user, OpenBoardUpdateReq req) {
        OpenBoard openBoard = openBoardRepository.findById(req.getIdx()).orElseThrow(()-> new BaseException(COMMUNITY_BOARD_NOT_FOUND));

        if(openBoard.getUser().getIdx()!= user.getIdx()){
            throw new BaseException(COMMUNITY_USER_NOT_AUTHOR);
        }
        openBoard.setTitle(req.getTitle());
        openBoard.setContent(req.getContent());
        openBoard.setImage(req.getImage());
        openBoard.setCreatedAt(LocalDateTime.now());

        openBoardRepository.save(openBoard);

        return OpenBoardRes.builder()
                .idx(openBoard.getIdx())
                .title(openBoard.getContent())
                .content(openBoard.getContent())
                .build();

    }

    public String delete(User user,Long idx) {
        OpenBoard openBoard = openBoardRepository.findById(idx).orElseThrow(()->new BaseException(COMMUNITY_BOARD_NOT_FOUND));
        if(!openBoard.getUser().getIdx().equals(user.getIdx())){
            throw new BaseException(COMMUNITY_USER_NOT_AUTHOR);
        }
        openBoardRepository.delete(openBoard);
        // TODO : 게시글 삭제 시, 해당 게시글의 댓글, 댓글좋아요, 대댓글, 대댓글좋아요 삭제
        return  "게시글 삭제";
    }


}
