package com.example.dopamines.global.aop;

import static com.example.dopamines.global.common.BaseResponseStatus.USER_NOT_AUTHENTICATED;

import com.example.dopamines.global.common.BaseException;
import com.example.dopamines.global.security.CustomUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class AuthenticationAspect {
    @Pointcut("@annotation(com.example.dopamines.global.common.annotation.CheckAuthentication)")
    private void cut(){
    }

    @Before("cut()")
    public void checkNull(JoinPoint joinPoint) {
        log.info("[AUTH][ASPECT] target : {}", joinPoint.getSignature());
        CustomUserDetails userDetails = getCurrentUserDetails();
        if (userDetails == null) {
            throw new BaseException(USER_NOT_AUTHENTICATED);
        }
    }

    private CustomUserDetails getCurrentUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof CustomUserDetails)) {
            return null;
        }

        return (CustomUserDetails) authentication.getPrincipal();
    }
}
