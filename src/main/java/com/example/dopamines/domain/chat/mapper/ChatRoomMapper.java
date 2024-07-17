package com.example.dopamines.domain.chat.mapper;


import com.example.dopamines.domain.chat.model.dto.ChatRoomDTO.Response;
import com.example.dopamines.domain.chat.model.entity.ChatRoom;
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
    ChatRoom toEntity(String name);


    Response toDto(ChatRoom entity);
}
