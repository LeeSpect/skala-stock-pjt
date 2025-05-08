package com.sk.skala.global.error;

import com.sk.skala.global.errorCode.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public ResponseEntity<ErrorResponse> handleBindException(BindException ex) {

        Map<String, String> errorMap = new HashMap<>();

        if (ex.hasErrors()) {
            BindingResult bindingResult = ex.getBindingResult();

            bindingResult.getFieldErrors().forEach(fieldError -> {
                errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
            });
        }
        String errorMapString = errorMap.toString();
        log.error("입력 에러 : {}", errorMapString);

        return ResponseEntity
                .badRequest()
                .body(new ErrorResponse(HttpStatus.BAD_REQUEST, errorMapString));
    }

    // 사용자 정의 에러 (컨트롤러에서 사용할 메서드)
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleException(CustomException ex) {

        ErrorCode errorCode = ex.getErrorCode();
        HttpStatus httpStatus = errorCode.getHttpStatus();

        ErrorResponse errorResponse = new ErrorResponse(httpStatus, errorCode.getMessage());

        log.error("사용자 정의 에러 : {}", errorCode.getMessage());
        return ResponseEntity.status(httpStatus).body(errorResponse);
    }

    // 모든 에러
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {

        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorResponse errorResponse = new ErrorResponse(httpStatus, ex.getMessage());

        log.error("서버 에러 : {}", ex.getMessage(), ex);
        return ResponseEntity.status(httpStatus).body(errorResponse);
    }
}
