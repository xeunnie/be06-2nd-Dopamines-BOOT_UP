package com.example.dopamines.domain.board.community.open.service;

import com.example.dopamines.domain.board.community.open.model.entity.OpenPost;
import com.example.dopamines.domain.board.community.open.model.entity.OpenPostImage;
import com.example.dopamines.domain.board.community.open.model.request.OpenPostUpdateReq;
import com.example.dopamines.domain.board.community.open.model.response.OpenCommentReadRes;
import com.example.dopamines.domain.board.community.open.model.response.OpenPostReadRes;
import com.example.dopamines.domain.board.community.open.model.response.OpenPostRes;
import com.example.dopamines.domain.board.community.open.model.request.OpenPostReq;
import com.example.dopamines.domain.board.community.open.repository.OpenPostImageRepository;
import com.example.dopamines.domain.board.community.open.repository.OpenPostRepository;
import com.example.dopamines.domain.user.model.entity.User;
import com.example.dopamines.global.common.BaseException;
import com.example.dopamines.global.common.BaseResponseStatus;
import com.example.dopamines.global.common.annotation.Timer;
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
import java.util.stream.Collectors;

import static com.example.dopamines.global.common.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class OpenPostService {
    private final OpenPostRepository openPostRepository;
    private final OpenCommentService openCommentService;
    private final OpenPostImageRepository openPostImageRepository;

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
//                .images(imageList)
                .createdAt(LocalDateTime.now())
                .build()
        );

        for (String url : imageUrlList) {
            openPostImageRepository.save(OpenPostImage.builder()
                    .url(url)
                    .openPost(openPost)
                    .build()
            );
        }

        return "공개 게시판 게시글 등록";
    }


    @Timer
    public OpenPostReadRes read(Long idx) {
        OpenPost openPost = openPostRepository.findByIdWithAuthor(idx).orElseThrow(() -> new BaseException(BaseResponseStatus.COMMUNITY_BOARD_NOT_FOUND));
        List<OpenCommentReadRes> openComments = openCommentService.findAllWithPage(idx, 0, 10);

        List<String> imageUrls = openPost.getImages().stream()
                .map((url) -> url.getUrl())
                .collect(Collectors.toList());

        return OpenPostReadRes.builder()
                .idx(openPost.getIdx())
                .title(openPost.getTitle())
                .content(openPost.getContent())
                .author(openPost.getUser().getNickname())
                .imageUrlList(imageUrls)
                .created_at(LocalDateTime.now())
                .likeCount(openPost.getLikesCount())
                .openCommentList(openComments)
                .build();
    }

    public List<OpenPostRes> readAll(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "idx"));
        Slice<OpenPost> result = openPostRepository.findAllWithPaging(pageable)
                .orElseThrow(()-> new BaseException(BaseResponseStatus.POST_NOT_FOUND));

        List<OpenPostRes> openPostResList = new ArrayList<>();

        for(OpenPost openPost : result.getContent()){
            openPostResList.add(OpenPostRes.builder()
                    .nickName(openPost.getUser().getNickname())
                    .idx(openPost.getIdx())
                    .title(openPost.getTitle())
                    .content(openPost.getContent())
                    .createdAt(openPost.getCreatedAt())
                    .build());
        }
        return openPostResList;
    }

    public OpenPostRes update(User user, OpenPostUpdateReq req, List<String> imageUrlList) {
        OpenPost openPost = openPostRepository.findById(req.getIdx()).orElseThrow(()-> new BaseException(BaseResponseStatus.COMMUNITY_BOARD_NOT_FOUND));

        if(!openPost.getUser().getIdx().equals(user.getIdx())){
            throw new BaseException(BaseResponseStatus.COMMUNITY_USER_NOT_AUTHOR);
        }
        List<OpenPostImage> imageList= new ArrayList<>();
        for (String url : imageUrlList) {
            OpenPostImage img = openPostImageRepository.save(OpenPostImage.builder()
                    .url(url)
                    .openPost(openPost)
                    .build()
            );
            imageList.add(img);
        }

        openPost.setTitle(req.getTitle());
        openPost.setContent(req.getContent());
        openPost.setImages(imageList);
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
