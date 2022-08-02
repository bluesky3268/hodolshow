package com.hyunbennylog.api.controller;

import com.hyunbennylog.api.request.PostCreate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


// SSR -> jsp, Thymeleaf, mustache, freemarker : 서버에서 화면 렌더링
// SPA  -> vue + SSR = nuxt
//      -> react + SSR = next.js
// : javascript가 화면을 만들어주고 서버와 통신은 API로만
/**
 * !!! 3번이상 반복을 한다면 뭔가가 잘못된 게 아닌가 의심해봐야 함 -> 개발자스럽지 못함 !!!
 */
@Slf4j
@RestController
public class PostController {

    // HTTP METHOD : GET, POST, PUT, PATCH, DELETE, CONNECT, OPTIONS, TRACE

    @PostMapping("/posts")
//    public String post(@RequestParam Map<String, String> params) {
    public Map<String, String> post(@RequestBody @Valid PostCreate params) {
        // 데이터 검증 이유
        // 1. 클라이언트에서 실수로 값을 안보내거나 잘못된 값을 보낼 수 있음
        // 2. 버그로 인해 값이 누락될 수있음
        // 3. 해킹하여 값을 조작하여 보낼 수 있음
        // 4. DB에 저장할 때 의도치 않은 오류가 발생할 수 있음

        log.info("params : {}", params.toString());
//        if (bindingResult.hasErrors()) {
//            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
//            FieldError firstFieldError = fieldErrors.get(0);
//            String fieldName = firstFieldError.getField();
//            String errorMessage = firstFieldError.getDefaultMessage();
//
//            Map<String, String> error = new HashMap<>();
//            error.put(fieldName, errorMessage);
//            log.info("fieldName : {}, errorMessage : {}", fieldName, errorMessage);
//            return error;
//        }

        return Map.of();
    }

}
