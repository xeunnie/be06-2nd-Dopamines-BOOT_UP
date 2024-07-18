package com.example.dopamines.domain.board.community.free.service;

import com.example.dopamines.domain.board.community.free.model.entity.FreeBoard;
import com.example.dopamines.domain.board.community.free.model.entity.FreeComment;
import com.example.dopamines.domain.board.community.free.model.entity.FreeRecomment;
import com.example.dopamines.domain.board.community.free.model.request.FreeBoardReq;
import com.example.dopamines.domain.board.community.free.model.request.FreeBoardUpdateReq;
import com.example.dopamines.domain.board.community.free.model.response.FreeBoardReadRes;
import com.example.dopamines.domain.board.community.free.model.response.FreeBoardRes;
import com.example.dopamines.domain.board.community.free.model.response.FreeCommentReadRes;
import com.example.dopamines.domain.board.community.free.model.response.FreeRecommentReadRes;
import com.example.dopamines.domain.board.community.free.repository.FreeBoardRepository;
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
public class FreeBoardService {
    private final FreeBoardRepository freeBoardRepository;

    @Transactional
    public String create(User user, FreeBoardReq req, List<String> imageUrlList) {

        if(req.getTitle() == null){
            throw new BaseException(COMMUNITY_TITLE_NOT_FOUND);
        }
        if(req.getContent() == null){
            throw new BaseException(COMMUNITY_CONTENT_NOT_FOUND);
        }
        FreeBoard freeBoard = freeBoardRepository.save(FreeBoard.builder()
                .title(req.getTitle())
                .content(req.getContent())
                .user(user)
                .imageUrlList(imageUrlList)
                .createdAt(LocalDateTime.now())
                .build()
        );

        return "자유 게시판 게시글 등록";
    }

    public FreeBoardReadRes read(Long idx) {
        FreeBoard freeBoard = freeBoardRepository.findById(idx).orElseThrow(() -> new BaseException(COMMUNITY_BOARD_NOT_FOUND));

        List<FreeCommentReadRes> freeCommentReadResList = new ArrayList<>();
        for(FreeComment freeComment : freeBoard.getComments()){
            List<FreeRecommentReadRes> freeRecommentReadResList = new ArrayList<>();
            for(FreeRecomment freeRecomment : freeComment.getFreeRecomments()){
                freeRecommentReadResList.add(FreeRecommentReadRes.builder()
                        .idx(freeRecomment.getIdx())
                        .freeBoardIdx(freeBoard.getIdx())
                        .commentIdx(freeRecomment.getFreeComment().getIdx())
                        .content(freeRecomment.getContent())
                        .author(freeRecomment.getUser().getNickname())
                        .createdAt(freeRecomment.getCreatedAt())
                        .likeCount(freeRecomment.getLikes().size())
                        .build());
            }
            freeCommentReadResList.add(FreeCommentReadRes.builder()
                    .idx(freeComment.getIdx())
                    .freeBoardIdx(freeBoard.getIdx())
                    .content(freeComment.getContent())
                    .author(freeComment.getUser().getNickname())
                    .createdAt(freeComment.getCreatedAt())
                    .likeCount(freeComment.getLikes().size())
                    .recommentList(freeRecommentReadResList)
                    .build());

        }

        return FreeBoardReadRes.builder()
                .idx(freeBoard.getIdx())
                .title(freeBoard.getTitle())
                .content(freeBoard.getContent())
                .author(freeBoard.getUser().getNickname())
//                .image(freeBoard.getImage())
                .created_at(LocalDateTime.now())
                .likeCount(freeBoard.getLikes().size())
                .freeCommentList(freeCommentReadResList)
                .build();
    }

    public List<FreeBoardRes> readAll(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "idx"));
        Slice<FreeBoard> result = freeBoardRepository.findAllWithPaging(pageable);
        List<FreeBoardRes> freeBoardResList = new ArrayList<>();

        for(FreeBoard freeBoard : result.getContent()){
            freeBoardResList.add(FreeBoardRes.builder()
                    .idx(freeBoard.getIdx())
                    .title(freeBoard.getTitle())
                    .content(freeBoard.getContent())
                    .build());
        }
        return freeBoardResList;
    }

    public FreeBoardRes update(User user, FreeBoardUpdateReq req) {
        FreeBoard freeBoard = freeBoardRepository.findById(req.getIdx()).orElseThrow(()-> new BaseException(COMMUNITY_BOARD_NOT_FOUND));

        if(freeBoard.getUser().getIdx()!= user.getIdx()){
            throw new BaseException(COMMUNITY_USER_NOT_AUTHOR);
        }
        freeBoard.setTitle(req.getTitle());
        freeBoard.setContent(req.getContent());
//        freeBoard.setImage(req.getImage());
        freeBoard.setCreatedAt(LocalDateTime.now());

        freeBoardRepository.save(freeBoard);

        return FreeBoardRes.builder()
                .idx(freeBoard.getIdx())
                .title(freeBoard.getContent())
                .content(freeBoard.getContent())
                .build();

    }

    public String delete(User user,Long idx) {
        FreeBoard freeBoard = freeBoardRepository.findById(idx).orElseThrow(()->new BaseException(COMMUNITY_BOARD_NOT_FOUND));
        if(!freeBoard.getUser().getIdx().equals(user.getIdx())){
            throw new BaseException(COMMUNITY_USER_NOT_AUTHOR);
        }
        freeBoardRepository.delete(freeBoard);
        // TODO : 게시글 삭제 시, 해당 게시글의 댓글, 댓글좋아요, 대댓글, 대댓글좋아요 삭제
        return  "게시글 삭제";
    }


}