package com.example.dopamines.domain.chat.mapper;


import com.example.dopamines.domain.board.market.model.entity.MarketPost;
import com.example.dopamines.domain.board.market.model.response.MarketReadRes;
import com.example.dopamines.domain.chat.model.entity.ChatRoom;
import com.example.dopamines.domain.chat.model.response.ChatRoomRes;
import java.time.LocalDate;
import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = ComponentModel.SPRING, imports = {UUID.class, LocalDate.class})
public interface ChatRoomMapper {
    ChatRoomMapper INSTANCE = Mappers.getMapper(ChatRoomMapper.class);

    @Mappings({
            @Mapping(target = "idx", expression = "java(UUID.randomUUID().toString())"),
            @Mapping(target = "createdAt", expression = "java(LocalDate.now())"),
            @Mapping(target = "updatedAt", expression = "java(LocalDate.now())"),
            @Mapping(target = "status", constant = "true")
    })
    ChatRoom toEntity(String buyer, MarketPost marketPost);


    @Mapping(target="idx", source = "entity.idx")
    ChatRoomRes toDto(ChatRoom entity, MarketReadRes product);
}
