package com.example.dopamines.domain.board.community.free.service;

import com.example.dopamines.domain.board.community.free.model.entity.FreePost;
import com.example.dopamines.domain.board.community.free.model.entity.FreeComment;
import com.example.dopamines.domain.board.community.free.model.entity.FreeRecomment;
import com.example.dopamines.domain.board.community.free.model.request.FreePostReq;
import com.example.dopamines.domain.board.community.free.model.request.FreePostUpdateReq;
import com.example.dopamines.domain.board.community.free.model.response.FreePostReadRes;
import com.example.dopamines.domain.board.community.free.model.response.FreePostRes;
import com.example.dopamines.domain.board.community.free.model.response.FreeCommentReadRes;
import com.example.dopamines.domain.board.community.free.model.response.FreeRecommentReadRes;
import com.example.dopamines.domain.board.community.free.repository.FreePostRepository;
import com.example.dopamines.domain.user.model.entity.User;
import com.example.dopamines.global.common.BaseException;
import com.example.dopamines.global.common.BaseResponseStatus;
import jakarta.persistence.EntityNotFoundException;
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
public class FreePostService {
    private final FreePostRepository freePostRepository;

    @Transactional
    public String create(User user, FreePostReq req, List<String> imageUrlList) {

        if(req.getTitle() == null){
            throw new BaseException(COMMUNITY_TITLE_NOT_FOUND);
        }
        if(req.getContent() == null){
            throw new BaseException(COMMUNITY_CONTENT_NOT_FOUND);
        }
        FreePost freePost = freePostRepository.save(FreePost.builder()
                .title(req.getTitle())
                .content(req.getContent())
                .user(user)
                .imageUrlList(imageUrlList)
                .createdAt(LocalDateTime.now())
                .build()
        );

        return "자유 게시판 게시글 등록";
    }

    public FreePostReadRes read(Long idx) {
        FreePost freePost = freePostRepository.findById(idx).orElseThrow(() -> new BaseException(BaseResponseStatus.COMMUNITY_BOARD_NOT_FOUND));

        List<FreeCommentReadRes> freeCommentReadResList = new ArrayList<>();
        for(FreeComment freeComment : freePost.getComments()){
            List<FreeRecommentReadRes> freeRecommentReadResList = new ArrayList<>();
            for(FreeRecomment freeRecomment : freeComment.getFreeRecomments()){
                freeRecommentReadResList.add(FreeRecommentReadRes.builder()
                        .idx(freeRecomment.getIdx())
                        .freePostIdx(freePost.getIdx())
                        .commentIdx(freeRecomment.getFreeComment().getIdx())
                        .content(freeRecomment.getContent())
                        .author(freeRecomment.getUser().getNickname())
                        .createdAt(freeRecomment.getCreatedAt())
                        .likeCount(freeRecomment.getLikesCount())
                        .build());
            }
            freeCommentReadResList.add(FreeCommentReadRes.builder()
                    .idx(freeComment.getIdx())
                    .freePostIdx(freePost.getIdx())
                    .content(freeComment.getContent())
                    .author(freeComment.getUser().getNickname())
                    .createdAt(freeComment.getCreatedAt())
                    .likeCount(freeComment.getLikesCount())
                    .recommentList(freeRecommentReadResList)
                    .build());

        }

        return FreePostReadRes.builder()
                .idx(freePost.getIdx())
                .title(freePost.getTitle())
                .content(freePost.getContent())
                .author(freePost.getUser().getNickname())
                .imageUrlList(freePost.getImageUrlList())
                .created_at(LocalDateTime.now())
                .likeCount(freePost.getLikesCount())
                .freeCommentList(freeCommentReadResList)
                .build();
    }

    public List<FreePostRes> readAll(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "idx"));
        Slice<FreePost> result = freePostRepository.findAllWithPaging(pageable)
                .orElseThrow(()-> new BaseException(BaseResponseStatus.POST_NOT_FOUND));
        List<FreePostRes> freePostResList = new ArrayList<>();

        for(FreePost freePost : result.getContent()){
            freePostResList.add(FreePostRes.builder()
                    .idx(freePost.getIdx())
                    .title(freePost.getTitle())
                    .content(freePost.getContent())
                    .build());
        }
        return freePostResList;
    }

    public FreePostRes update(User user, FreePostUpdateReq req, List<String> imageUrlList) {
        FreePost freePost = freePostRepository.findById(req.getIdx()).orElseThrow(()-> new BaseException(BaseResponseStatus.COMMUNITY_BOARD_NOT_FOUND));

        if(!freePost.getUser().getIdx().equals(user.getIdx())){
            throw new BaseException(BaseResponseStatus.COMMUNITY_USER_NOT_AUTHOR);
        }
        freePost.setTitle(req.getTitle());
        freePost.setContent(req.getContent());
        freePost.setImageUrlList(imageUrlList);
        freePost.setCreatedAt(LocalDateTime.now());

        freePostRepository.save(freePost);

        return FreePostRes.builder()
                .idx(freePost.getIdx())
                .title(freePost.getContent())
                .content(freePost.getContent())
                .build();

    }

    public void delete(User user,Long idx) {
        FreePost freePost = freePostRepository.findById(idx).orElseThrow(()->new BaseException(BaseResponseStatus.COMMUNITY_BOARD_NOT_FOUND));
        if(!freePost.getUser().getIdx().equals(user.getIdx())){
            throw new BaseException(BaseResponseStatus.COMMUNITY_USER_NOT_AUTHOR);
        }

        try {
            freePostRepository.delete(freePost);
        } catch (EntityNotFoundException e) {
            throw new BaseException(BaseResponseStatus.COMMUNITY_BOARD_NOT_FOUND);
        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.COMMUNITY_BOARD_DELETE_FAILED);
        }

        // TODO : 게시글 삭제 시, 해당 게시글의 댓글, 댓글좋아요, 대댓글, 대댓글좋아요 삭제
    }


    public List<FreePostRes> search(Integer page, Integer size, String keyword) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "idx"));
        Slice<FreePost> result = freePostRepository.search(pageable,keyword)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.COMMUNITY_BOARD_NOT_FOUND));
        List<FreePostRes> freePostResList = new ArrayList<>();

        for(FreePost freePost : result.getContent()){
            freePostResList.add(FreePostRes.builder()
                    .idx(freePost.getIdx())
                    .title(freePost.getTitle())
                    .content(freePost.getContent())
                    .build());
        }
        return freePostResList;
    }
}