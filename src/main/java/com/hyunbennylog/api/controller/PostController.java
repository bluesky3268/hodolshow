package com.hyunbennylog.api.controller;

import com.hyunbennylog.api.domain.Post;
import com.hyunbennylog.api.exception.InvalidRequestException;
import com.hyunbennylog.api.request.PostCreate;
import com.hyunbennylog.api.request.PostModification;
import com.hyunbennylog.api.request.PostSearch;
import com.hyunbennylog.api.response.PostResponse;
import com.hyunbennylog.api.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
@RequiredArgsConstructor
public class PostController {
    // HTTP METHOD : GET, POST, PUT, PATCH, DELETE, CONNECT, OPTIONS, TRACE

    private final PostService postService;

    // 게시글 등록
    @PostMapping("/posts")
    public void post(@RequestBody @Valid PostCreate request) {
        log.info("request : {}", request.toString());
        // 데이터 검증 이유
        // 1. 클라이언트에서 실수로 값을 안보내거나 잘못된 값을 보낼 수 있음
        // 2. 버그로 인해 값이 누락될 수있음
        // 3. 해킹하여 값을 조작하여 보낼 수 있음
        // 4. DB에 저장할 때 의도치 않은 오류가 발생할 수 있음

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

        request.validate();

        postService.regist(request);
        /** POST요청에 대한 응답
           1. 은 200, 201을 주로 사용함.
           2-1. 클라이언트의 요청에 의해 저장한 데이터를 그대로 주는 경우,
           2-2. 저장한 데이터의 PK만 주는 경우도 있음 -> PK만 받는 경우 조회 API를 다시 호출하여 데이터를 받음
           3. 응답 필요없음 -> 게시글 작성 후 목록으로 이동하는 경우
         
           안좋은 경우 
            -> '서버에서는 무조건 이렇게 내려줌' -> 다양한 요구사항에 대해 유연하게 개발해야 함
        */

    }

    // 단건 조회
    @GetMapping("/posts/{postId}")
    public PostResponse getPost(@PathVariable Long postId) {
        log.info("postId : {}", postId);
       return postService.getPost(postId);
    }

    // 리스트 조회
    @GetMapping("/posts")
    public List<PostResponse> getPostList(@ModelAttribute PostSearch postSearch) {
        // size를 parameter로 넘겨받아도 되고
        // yml에 기본설정값을 세팅해놔도 되고
        // @PageableDefault(size=5) 이런식으로도 사용가능
        return postService.getPostList(postSearch);
    }

    // 게시글 수정
    @PatchMapping("/posts/{postId}")
    public PostResponse modifyPost(@PathVariable Long postId, @RequestBody @Valid PostModification request){
        log.info("request : {}", request.toString());
        return postService.modify(postId, request);
    }

    @DeleteMapping("/posts/{postId}")
    public void deletePost(@PathVariable Long postId){
        log.info("postId : {}", postId);
        postService.delete(postId);
    }

}
