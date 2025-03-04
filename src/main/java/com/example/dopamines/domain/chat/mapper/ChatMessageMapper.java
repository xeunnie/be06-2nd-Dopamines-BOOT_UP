package com.example.dopamines.domain.chat.mapper;

import com.example.dopamines.domain.chat.model.entity.ChatMessage;
import com.example.dopamines.domain.chat.model.entity.ChatRoom;
import com.example.dopamines.domain.chat.model.request.ChatMessageReq;
import com.example.dopamines.domain.chat.model.response.ChatMessageRes;
import com.example.dopamines.domain.user.model.entity.User;
import java.time.LocalDate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = ComponentModel.SPRING, imports = {LocalDate.class})
public interface ChatMessageMapper {
    ChatMessageMapper INSTANCE = Mappers.getMapper(ChatMessageMapper.class);

    @Mappings({
            @Mapping(target = "idx", ignore = true),
            @Mapping(target = "sender", source = "sender"),
            @Mapping(target = "createdAt", expression = "java(LocalDate.now())")
    })
    ChatMessage toEntity(ChatMessageReq req, User sender, ChatRoom chatRoom);


    @Mapping(target="sender", source="senderName")
    ChatMessageRes toDto(ChatMessage entity, String senderName, Long senderIdx);
}
