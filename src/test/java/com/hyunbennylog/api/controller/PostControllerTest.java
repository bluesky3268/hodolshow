package com.hyunbennylog.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyunbennylog.api.domain.Post;
import com.hyunbennylog.api.repository.PostRepository;
import com.hyunbennylog.api.request.PostCreate;
import com.hyunbennylog.api.service.PostService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@WebMvcTest
@SpringBootTest
@AutoConfigureMockMvc
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostService postService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void clean(){
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("/posts 요청시 Hello World출력")
    void request_posts_printHelloWorld() throws Exception {
        PostCreate request = PostCreate.builder()
                .title("제목")
                .content("내용입니당!!")
                .build();

        String requestToJson = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestToJson)
                )
                .andExpect(status().isOk())
                .andExpect(content().string(""))
                .andDo(print());

    }

    @Test
    @DisplayName("/posts 요청시 title값은 필수")
    void request_posts_titleIsNotNull() throws Exception {
        PostCreate request = PostCreate.builder()
                .content("내용입니당!!")
                .build();

        String requestToJson = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
//                .content("{\"title\":\"\", \"content\":\"내용입니다..\"}"))
                .content(requestToJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(jsonPath("$.validation.title").value("제목은 필수값입니다."))
                /**
                 *jsonPath 찾아보기
                 */
                .andDo(print());

    }

    @Test
    @DisplayName("/posts 요청시 DB에 저장")
    void request_posts_saveDataToDB() throws Exception {
        // when
        PostCreate request = PostCreate.builder()
                .title("제목")
                .content("내용입니당!!")
                .build();

        String requestToJson = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestToJson))
                .andExpect(status().isOk())
                .andDo(print());

        // then
        Assertions.assertEquals(1L, postRepository.count());

        // 저장이 제대로 되었는지 검증
        Post post = postRepository.findAll().get(0);
        assertEquals("제목", post.getTitle());
        assertEquals("내용입니당!!", post.getContent());


        /**
         *     -> 각 테스트들은 다른 테스트에 영향을 끼치지 않아야 함
         *        테스트를 각각 실행할 때는 문제가 없지만 전체를 한 번에 실행하면 test1()에서 성공케이스가 나오므로
         *       test3()에서의 카운트는 2가 됨
         *        각 메서드들을 실행하기 전에 깨끗한 상태에서 테스트를 수행할 수 있는 메서드를 만드는 것이 좋음
         */
    }

    @Test
    @DisplayName("게시글 조회")
    void findPost() throws Exception {
        // given
        Post request = Post.builder()
                .title("게시글제목1234567890")
                .content("게시글 내용")
                .build();

        postRepository.save(request);

        // when //then
        mockMvc.perform(MockMvcRequestBuilders.get("/posts/{postId}", request.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(request.getId()))
                .andExpect(jsonPath("$.title").value("게시글제목"))
                .andExpect(jsonPath("$.content").value("게시글 내용"))
                .andDo(print())
        ;
    }


    @Test
    @DisplayName("게시글 목록 조회")
    void getPostList() throws Exception {
        // given
        Post post1 = Post.builder()
                .title("1234567890")
                .content("content1")
                .build();

        Post post2 = Post.builder()
                .title("abcdefghijklmn")
                .content("content2")
                .build();

        postRepository.saveAll(List.of(post1, post2));

        // expected
        mockMvc.perform(get("/posts")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", Matchers.is(2)))
                .andExpect(jsonPath("$.[0].title").value("12345"))
                .andExpect(jsonPath("$.[0].content").value("content1"))
                .andExpect(jsonPath("$.[1].title").value("abcde"))
                .andExpect(jsonPath("$.[1].content").value("content2"))
                .andDo(print())
        ;

    }

}