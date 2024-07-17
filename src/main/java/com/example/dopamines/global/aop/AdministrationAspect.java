package com.example.dopamines.global.aop;

import com.example.dopamines.global.common.BaseException;
import com.example.dopamines.global.common.BaseResponseStatus;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
public class AdministrationAspect {
    @Pointcut("@annotation(com.example.dopamines.global.common.annotation.CheckAdmin)")
    public void adminCheck() {
    }

    public boolean isAdmin() {
        //TODO 관리자 확인 작업 로직 필요
        return true;
    }

    @Before("adminCheck()")
    public void checkAdmin() {
        log.info("[ADMIN][ASPECT] check admin");
        // 관리자 권한 체크
        if (!isAdmin()) {
            throw new BaseException(BaseResponseStatus.NOTICE_NOT_AUTHORIZED);
        }
    }
}
