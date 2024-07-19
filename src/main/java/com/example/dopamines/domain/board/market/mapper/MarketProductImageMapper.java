package com.example.dopamines.domain.board.market.mapper;

import com.example.dopamines.domain.board.market.model.entity.MarketPost;
import com.example.dopamines.domain.board.market.model.entity.MarketProductImage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = ComponentModel.SPRING)
public interface MarketProductImageMapper {
    MarketProductImageMapper INSTANCE = Mappers.getMapper(MarketProductImageMapper.class);
    @Mapping(target = "idx", ignore = true)
    MarketProductImage toEntity(String url, MarketPost marketPost);
}
