package com.example.dopamines.domain.board.community.open.service;

import com.example.dopamines.domain.board.community.open.model.entity.OpenComment;
import com.example.dopamines.domain.board.community.open.model.entity.OpenPost;
import com.example.dopamines.domain.board.community.open.model.entity.OpenRecomment;
import com.example.dopamines.domain.board.community.open.model.request.OpenPostReq;
import com.example.dopamines.domain.board.community.open.model.request.OpenPostUpdateReq;
import com.example.dopamines.domain.board.community.open.model.response.OpenCommentReadRes;
import com.example.dopamines.domain.board.community.open.model.response.OpenPostReadRes;
import com.example.dopamines.domain.board.community.open.model.response.OpenPostRes;
import com.example.dopamines.domain.board.community.open.model.response.OpenRecommentReadRes;
import com.example.dopamines.domain.board.community.open.repository.OpenPostRepository;
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
public class OpenPostService {
    private final OpenPostRepository openPostRepository;

    @Transactional
    public String create(User user, OpenPostReq req, List<String> imageUrlList) {

        if(req.getTitle() == null){
            throw new BaseException(COMMUNITY_TITLE_NOT_FOUND);
        }
        if(req.getContent() == null){
            throw new BaseException(COMMUNITY_CONTENT_NOT_FOUND);
        }
        OpenPost openPost = openPostRepository.save(OpenPost.builder()
                .title(req.getTitle())
                .content(req.getContent())
                .user(user)
                .imageUrlList(imageUrlList)
                .createdAt(LocalDateTime.now())
                .build()
        );

        return "자유 게시판 게시글 등록";
    }

    public OpenPostReadRes read(Long idx) {
        OpenPost openPost = openPostRepository.findById(idx).orElseThrow(() -> new BaseException(BaseResponseStatus.COMMUNITY_BOARD_NOT_FOUND));

        List<OpenCommentReadRes> openCommentReadResList = new ArrayList<>();
        for(OpenComment openComment : openPost.getComments()){
            List<OpenRecommentReadRes> openRecommentReadResList = new ArrayList<>();
            for(OpenRecomment openRecomment : openComment.getOpenRecomments()){
                openRecommentReadResList.add(OpenRecommentReadRes.builder()
                        .idx(openRecomment.getIdx())
                        .openPostIdx(openPost.getIdx())
                        .commentIdx(openRecomment.getOpenComment().getIdx())
                        .content(openRecomment.getContent())
                        .author(openRecomment.getUser().getNickname())
                        .createdAt(openRecomment.getCreatedAt())
                        .likeCount(openRecomment.getLikes().size())
                        .build());
            }
            openCommentReadResList.add(OpenCommentReadRes.builder()
                    .idx(openComment.getIdx())
                    .openPostIdx(openPost.getIdx())
                    .content(openComment.getContent())
                    .author(openComment.getUser().getNickname())
                    .createdAt(openComment.getCreatedAt())
                    .likeCount(openComment.getLikes().size())
                    .recommentList(openRecommentReadResList)
                    .build());

        }

        return OpenPostReadRes.builder()
                .idx(openPost.getIdx())
                .title(openPost.getTitle())
                .content(openPost.getContent())
                .author(openPost.getUser().getNickname())
                .imageUrlList(openPost.getImageUrlList())
                .created_at(LocalDateTime.now())
                .likeCount(openPost.getLikes().size())
                .openCommentList(openCommentReadResList)
                .build();
    }

    public List<OpenPostRes> readAll(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "idx"));
        Slice<OpenPost> result = openPostRepository.findAllWithPaging(pageable)
                .orElseThrow(()-> new BaseException(BaseResponseStatus.POST_NOT_FOUND));
        List<OpenPostRes> openPostResList = new ArrayList<>();

        for(OpenPost openPost : result.getContent()){
            openPostResList.add(OpenPostRes.builder()
                    .idx(openPost.getIdx())
                    .title(openPost.getTitle())
                    .content(openPost.getContent())
                    .build());
        }
        return openPostResList;
    }

    public OpenPostRes update(User user, OpenPostUpdateReq req, List<String> imageUrlList) {
        OpenPost openPost = openPostRepository.findById(req.getIdx()).orElseThrow(()-> new BaseException(BaseResponseStatus.COMMUNITY_BOARD_NOT_FOUND));

        if(!openPost.getUser().getIdx().equals(user.getIdx())){
            throw new BaseException(BaseResponseStatus.COMMUNITY_USER_NOT_AUTHOR);
        }
        openPost.setTitle(req.getTitle());
        openPost.setContent(req.getContent());
        openPost.setImageUrlList(imageUrlList);
        openPost.setCreatedAt(LocalDateTime.now());

        openPostRepository.save(openPost);

        return OpenPostRes.builder()
                .idx(openPost.getIdx())
                .title(openPost.getContent())
                .content(openPost.getContent())
                .build();

    }

    public void delete(User user,Long idx) {
        OpenPost openPost = openPostRepository.findById(idx).orElseThrow(()->new BaseException(BaseResponseStatus.COMMUNITY_BOARD_NOT_FOUND));
        if(!openPost.getUser().getIdx().equals(user.getIdx())){
            throw new BaseException(BaseResponseStatus.COMMUNITY_USER_NOT_AUTHOR);
        }

        try {
            openPostRepository.delete(openPost);
        } catch (EntityNotFoundException e) {
            throw new BaseException(BaseResponseStatus.COMMUNITY_BOARD_NOT_FOUND);
        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.COMMUNITY_BOARD_DELETE_FAILED);
        }

        // TODO : 게시글 삭제 시, 해당 게시글의 댓글, 댓글좋아요, 대댓글, 대댓글좋아요 삭제
    }


    public List<OpenPostRes> search(Integer page, Integer size, String keyword) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "idx"));
        Slice<OpenPost> result = openPostRepository.search(pageable,keyword)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.COMMUNITY_BOARD_NOT_FOUND));
        List<OpenPostRes> openPostResList = new ArrayList<>();

        for(OpenPost openPost : result.getContent()){
            openPostResList.add(OpenPostRes.builder()
                    .idx(openPost.getIdx())
                    .title(openPost.getTitle())
                    .content(openPost.getContent())
                    .build());
        }
        return openPostResList;
    }
}
