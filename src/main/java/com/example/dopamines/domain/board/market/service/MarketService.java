package com.example.dopamines.domain.board.market.service;


import com.example.dopamines.domain.board.market.mapper.MarketPostMapper;
import com.example.dopamines.domain.board.market.mapper.MarketProductImageMapper;
import com.example.dopamines.domain.board.market.model.entity.MarketPost;
import com.example.dopamines.domain.board.market.model.entity.MarketProductImage;
import com.example.dopamines.domain.board.market.model.request.MarketCreateReq;
import com.example.dopamines.domain.board.market.model.response.MarketDetailRes;
import com.example.dopamines.domain.board.market.model.response.MarketReadRes;
import com.example.dopamines.domain.board.market.repository.MarketPostRepository;
import com.example.dopamines.domain.board.market.repository.MarketProductImageRepository;
import com.example.dopamines.domain.user.model.entity.User;
import com.example.dopamines.domain.user.repository.UserRepository;
import com.example.dopamines.global.common.BaseException;
import java.util.List;
import java.util.stream.Collectors;

import com.example.dopamines.global.common.BaseResponseStatus;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MarketService {
    private final MarketPostRepository postRepository;
    private final MarketProductImageRepository marketProductImageRepository;
    private final UserRepository userRepository;
    private final MarketPostMapper marketPostMapper;
    private final MarketProductImageMapper marketProductImageMapper;

    public MarketReadRes add(List<String> imageUrls, MarketCreateReq req, User user) {
        String mainImage = imageUrls.get(0);
        MarketPost post = marketPostMapper.toEntity(mainImage, req, user);
        postRepository.save(post);

        for (String url : imageUrls) {
            MarketProductImage marketProductImage = marketProductImageMapper.toEntity(url, post);
            marketProductImageRepository.save(marketProductImage);
        }

        user = userRepository.findById(user.getIdx()).orElseThrow(() -> new BaseException(BaseResponseStatus.USER_NOT_FOUND));
        return marketPostMapper.toDto(post,user.getNickname());
    }

    public MarketDetailRes findById(Long idx) {
        MarketPost post = postRepository.findByIdWithImages(idx);

        return marketPostMapper.toDetailDto(post, post.getUser().getNickname());
    }

    public List<MarketReadRes> findAll(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "idx"));
        Slice<MarketPost> posts = postRepository.findAllWithPaging(pageable);

        return posts.stream()
                .map((post) -> marketPostMapper.toDto(post, post.getUser().getNickname()))
                .collect(Collectors.toList());
    }

    public List<MarketReadRes> search(Integer page, Integer size, String keyword, Integer minPrice, Integer maxPrice) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "idx"));
        Slice<MarketPost> posts = postRepository.search(pageable, keyword, minPrice, maxPrice);

        return posts.stream()
                .map((post) -> marketPostMapper.toDto(post, post.getUser().getNickname()))
                .collect(Collectors.toList());
    }

    public void updateStatus(Long idx) {
        MarketPost post = postRepository.findById(idx).orElseThrow(() -> new BaseException(BaseResponseStatus.MARKET_NOT_FOUND));

        if (!post.isStatus()) { // 상품이 판매되지 않은 상태
            post.setStatus(true); // 판매 완료로 변경
            postRepository.save(post);
        }
    }

    @Transactional
    public void delete(Long idx) {
        try {
            postRepository.deleteById(idx);
        } catch (EntityNotFoundException e) {
            throw new BaseException(BaseResponseStatus.MARKET_NOT_FOUND);
        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.MARKET_DELETE_FAILED);
        }
    }
}
