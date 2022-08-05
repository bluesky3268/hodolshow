package com.hyunbennylog.api.controller;

import com.hyunbennylog.api.exception.HyunbennylogException;
import com.hyunbennylog.api.exception.InvalidRequestException;
import com.hyunbennylog.api.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Slf4j
@ControllerAdvice
public class ExceptionController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ErrorResponse invalidRequestHandler(MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);

        ErrorResponse response = ErrorResponse.builder()
                .code("400")
                .message("잘못된 요청입니다.")
                .build();

        for (FieldError field : e.getFieldErrors()) {
            response.addValidation(field.getField(), field.getDefaultMessage());
        }


        return response;
    }

    @ExceptionHandler(HyunbennylogException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> hyunbennylogExceptionHandler(HyunbennylogException e) {
        log.error(e.getMessage(), e);

        ErrorResponse body = ErrorResponse.builder()
                .code(String.valueOf(e.getStatusCode()))
                .message(e.getMessage())
                .validation(e.getValidation())
                .build();

        ResponseEntity<ErrorResponse> responseEntity = ResponseEntity.status(e.getStatusCode())
                .body(body);

        return responseEntity;
    }

}
