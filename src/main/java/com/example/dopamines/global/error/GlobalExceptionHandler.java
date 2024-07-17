package com.example.dopamines.global.error;

import com.example.dopamines.global.common.BaseException;
import com.example.dopamines.global.common.BaseResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<BaseResponse> handleException(BaseException e) {
        log.error("[ERROR] => code: {} {}", e.getStatus().getCode(), e.getStatus());
        return ResponseEntity.badRequest().body(new BaseResponse<>(e.getStatus()));
    }
}
