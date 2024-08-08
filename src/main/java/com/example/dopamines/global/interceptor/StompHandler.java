package com.example.dopamines.global.interceptor;

import com.example.dopamines.global.security.JwtUtil;
import java.nio.file.AccessDeniedException;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class StompHandler implements ChannelInterceptor {

    //private final JwtUtil jwtTokenProvider;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
//        if(accessor.getCommand() == StompCommand.CONNECT) {
//            if(!jwtTokenProvider.isExpired(accessor.getFirstNativeHeader("token"))) {
//                try {
//                    throw new AccessDeniedException("");
//                } catch (AccessDeniedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }

        return message;
    }
}