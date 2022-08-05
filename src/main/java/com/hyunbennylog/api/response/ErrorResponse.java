package com.hyunbennylog.api.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * {
 *     "code":"400",
 *     "message":"잘못된 요청입니다",
 *     "validation":{
 *         "title":"제목은 필수값입니다."
 *     }
 * }
 */

@Getter
//@JsonInclude(value = JsonInclude.Include.NON_EMPTY) // 비어있으면 빼고 응답함
public class ErrorResponse {

    private String code;
    private String message;
    private Map<String, String> validation;

    @Builder
    public ErrorResponse(String code, String message, Map<String, String> validation) {
        this.code = code;
        this.message = message;
        this.validation = validation != null ? validation : new HashMap<>();
    }

    public void addValidation(String fieldName, String errorMessage) {
        this.validation.put(fieldName, errorMessage);
    }

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", validation=" + validation +
                '}';
    }
}
