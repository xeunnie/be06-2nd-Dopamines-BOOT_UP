package com.example.dopamines.domain.board.market.service;


import static com.example.dopamines.global.common.BaseResponseStatus.POST_NOT_FOUND;
import static com.example.dopamines.global.common.BaseResponseStatus.USER_NOT_FOUND;

import com.example.dopamines.domain.board.market.mapper.MarketBoardMapper;
import com.example.dopamines.domain.board.market.mapper.ProductImageMapper;
import com.example.dopamines.domain.board.market.model.dto.MarketBoardDTO.DetailResponse;
import com.example.dopamines.domain.board.market.model.dto.MarketBoardDTO.Request;
import com.example.dopamines.domain.board.market.model.dto.MarketBoardDTO.Response;
import com.example.dopamines.domain.board.market.model.entity.MarketPost;
import com.example.dopamines.domain.board.market.model.entity.ProductImage;
import com.example.dopamines.domain.board.market.repository.MarketPostRepository;
import com.example.dopamines.domain.board.market.repository.ProductImageRepository;
import com.example.dopamines.domain.user.model.entity.User;
import com.example.dopamines.domain.user.repository.UserRepository;
import com.example.dopamines.global.common.BaseException;
import java.util.List;
import java.util.stream.Collectors;
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
    private final ProductImageRepository productImageRepository;
    private final UserRepository userRepository;
    private final MarketBoardMapper marketBoardMapper;
    private final ProductImageMapper productImageMapper;

    public Response add(List<String> imageUrls, Request req, User user) {
        String mainImage = imageUrls.get(0);
        MarketPost post = marketBoardMapper.toEntity(mainImage, req, user);
        postRepository.save(post);

        for (String url : imageUrls) {
            ProductImage productImage = productImageMapper.toEntity(url, post);
            productImageRepository.save(productImage);
        }

        user = userRepository.findById(user.getIdx()).orElseThrow(() -> new BaseException(USER_NOT_FOUND));
        return marketBoardMapper.toDto(post,user.getNickname());
    }

    public DetailResponse findById(Long idx) {
        MarketPost post = postRepository.findByIdWithImages(idx).orElseThrow(()-> new BaseException(POST_NOT_FOUND));

        return marketBoardMapper.toDetailDto(post, post.getUser().getNickname());
    }

    public List<Response> findAll(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "idx"));
        Slice<MarketPost> posts = postRepository.findAllWithPaging(pageable);

        return posts.stream()
                .map((post) -> marketBoardMapper.toDto(post, post.getUser().getNickname()))
                .collect(Collectors.toList());
    }

    public List<Response> search(Integer page, Integer size, String keyword, Integer minPrice, Integer maxPrice) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "idx"));
        Slice<MarketPost> posts = postRepository.search(pageable, keyword, minPrice, maxPrice);

        return posts.stream()
                .map((post) -> marketBoardMapper.toDto(post, post.getUser().getNickname()))
                .collect(Collectors.toList());
    }

    public void updateStatus(Long idx) {
        MarketPost post = postRepository.findById(idx).orElseThrow(() -> new BaseException(POST_NOT_FOUND));

        if (!post.isStatus()) { // 상품이 판매되지 않은 상태
            post.setStatus(true); // 판매 완료로 변경
            postRepository.save(post);
        }
    }

    @Transactional
    public void delete(Long idx) {
        postRepository.deleteById(idx);
    }
}
