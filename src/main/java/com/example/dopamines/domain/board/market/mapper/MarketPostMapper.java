package com.example.dopamines.domain.board.market.mapper;

import com.example.dopamines.domain.board.market.model.entity.MarketPost;
import com.example.dopamines.domain.board.market.model.entity.MarketProductImage;
import com.example.dopamines.domain.board.market.model.request.MarketCreateReq;
import com.example.dopamines.domain.board.market.model.response.MarketDetailRes;
import com.example.dopamines.domain.board.market.model.response.MarketReadRes;
import com.example.dopamines.domain.user.model.entity.User;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = ComponentModel.SPRING, imports = {LocalDate.class})
public interface MarketPostMapper {
    MarketPostMapper INSTANCE = Mappers.getMapper(MarketPostMapper.class);

    @Mappings({
            @Mapping(target = "idx", ignore = true),
            @Mapping(target = "createdAt", expression = "java(LocalDate.now())"),
            @Mapping(target = "updatedAt", expression = "java(LocalDate.now())"),
            @Mapping(target = "status", constant = "false")
    })
    MarketPost toEntity(String mainImage, MarketCreateReq dto, User user);

    MarketReadRes toDto(MarketPost entity, String author);
    @Mapping(source = "entity.images", target = "images")
    MarketDetailRes toDetailDto(MarketPost entity, String author, Long authorIdx);
    default List<String> map(List<MarketProductImage> images) {
        return images.stream()
                .map(MarketProductImage::getUrl)
                .collect(Collectors.toList());
    }

}
