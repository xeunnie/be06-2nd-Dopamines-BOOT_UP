package com.example.dopamines.domain.board.market.service;

import com.example.dopamines.domain.board.market.mapper.MarketPostMapper;
import com.example.dopamines.domain.board.market.model.entity.MarkedPost;
import com.example.dopamines.domain.board.market.model.entity.MarketPost;
import com.example.dopamines.domain.board.market.model.response.MarketReadRes;
import com.example.dopamines.domain.board.market.repository.MarkedPostRepository;
import com.example.dopamines.domain.board.market.repository.MarketPostRepository;
import com.example.dopamines.domain.user.model.entity.User;
import com.example.dopamines.global.common.BaseException;
import com.example.dopamines.global.common.BaseResponseStatus;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MarkedService {
    private final String SUCCESS_MARKED_DELETE = "찜 게시글 취소 성공";
    private final String SUCCESS_MARKED_CREATE = "찜 게시글 등록 완료";
    private final MarkedPostRepository markedRepository;
    private final MarketPostRepository marketPostRepository;

    private final MarketPostMapper mapper;
    public String create(User user, Long postIdx) {

        MarkedPost result = markedRepository.findByUserAndMarketPost(user.getIdx(), postIdx);
        MarkedPost marked = null;

        if (result != null) { // 이미 찜 되어 있는 경우
            markedRepository.deleteById(result.getIdx());
            return SUCCESS_MARKED_DELETE;
        }

        MarketPost post = marketPostRepository.findById(postIdx).orElseThrow(()-> new BaseException(BaseResponseStatus.MARKET_NOT_FOUND));
        marked = MarkedPost.builder()
                .user(user)
                .marketPost(post)
                .build();

       markedRepository.save(marked);

       return SUCCESS_MARKED_CREATE;
    }

    public boolean checkMarked(User user, Long postIdx) {
        MarketPost post = marketPostRepository.findById(postIdx).orElseThrow(()-> new BaseException(BaseResponseStatus.MARKET_NOT_FOUND));
        return markedRepository.existsByUserAndMarketPost(user, post);
    }

    public List<MarketReadRes> findAll(User user, Integer page, Integer size) {
        List<Long> postIds = markedRepository.findPostIdsByUserId(user.getIdx());

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "idx"));
        Slice<MarketPost> posts = marketPostRepository.findAllInMarked(postIds, pageable);

        return posts.stream()
                .map((post) -> mapper.toDto(post, post.getUser().getNickname()))
                .collect(Collectors.toList());
    }
}
