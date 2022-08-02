package com.hyunbennylog.api.controller;

import com.hyunbennylog.api.controller.request.PostCreate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


// SSR -> jsp, Thymeleaf, mustache, freemarker : 서버에서 화면 렌더링
// SPA  -> vue + SSR = nuxt
//      -> react + SSR = next.js
// : javascript가 화면을 만들어주고 서버와 통신은 API로만
@Slf4j
@RestController
public class PostController {

    // HTTP METHOD : GET, POST, PUT, PATCH, DELETE, CONNECT, OPTIONS, TRACE

    @PostMapping("/posts")
//    public String post(@RequestParam Map<String, String> params) {
    public String post(@RequestBody PostCreate params) {
        log.info("params : {}", params.toString());

        return "Hello World";
    }

}
