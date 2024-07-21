package com.example.dopamines.global.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class MethodExcutionTimer {
    private static final Logger logger = LoggerFactory.getLogger(MethodExcutionTimer.class);

    // 모든 메소드 다 측정할거면, 아까처럼 패키지로 pointcut 해주면 됨

    //특정 어노테이션이 달려있는 메소드들 만 하고 싶다. -> custom annotation 만듦
    @Pointcut("@annotation(com.example.dopamines.global.common.annotation.Timer)")
    private void timerPointcut(){
    }

    @Around("timerPointcut()")
    public Object traceTIme(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch stopWatch = new StopWatch();

        try {
            stopWatch.start();
            return joinPoint.proceed(); // 실제 객체의 메소드 실행
        }finally {
            stopWatch.stop();
            logger.info("{} - 시간 - {}s", joinPoint.getSignature().toString(), stopWatch.getTotalTimeMillis());
        }
    }

}
