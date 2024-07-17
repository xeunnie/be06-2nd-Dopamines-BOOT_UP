package com.example.dopamines.domain.board.market.mapper;

import com.example.dopamines.domain.board.market.model.dto.MarketBoardDTO.DetailResponse;
import com.example.dopamines.domain.board.market.model.dto.MarketBoardDTO.Request;
import com.example.dopamines.domain.board.market.model.dto.MarketBoardDTO.Response;
import com.example.dopamines.domain.board.market.model.entity.MarketPost;
import com.example.dopamines.domain.board.market.model.entity.ProductImage;
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
public interface MarketBoardMapper {
    MarketBoardMapper INSTANCE = Mappers.getMapper(MarketBoardMapper.class);

    @Mappings({
            @Mapping(target = "idx", ignore = true),
            @Mapping(target = "createdAt", expression = "java(LocalDate.now())"),
            @Mapping(target = "updatedAt", expression = "java(LocalDate.now())"),
            @Mapping(target = "status", constant = "false")
    })
    MarketPost toEntity(String mainImage, Request dto, User user);

    Response toDto(MarketPost entity, String author);
    @Mapping(source = "entity.images", target = "images")
    DetailResponse toDetailDto(MarketPost entity, String author);
    default List<String> map(List<ProductImage> images) {
        return images.stream()
                .map(ProductImage::getUrl)
                .collect(Collectors.toList());
    }

}
