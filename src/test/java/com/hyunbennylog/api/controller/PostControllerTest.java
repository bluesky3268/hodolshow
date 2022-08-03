package com.hyunbennylog.api.controller;

import com.hyunbennylog.api.domain.Post;
import com.hyunbennylog.api.repository.PostRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

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

    @BeforeEach
    void clean(){
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("/posts 요청시 Hello World출력")
    void test1() throws Exception {
        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"제목\", \"content\":\"내용입니다..\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("{}"))
                .andDo(print());

    }

    @Test
    @DisplayName("/posts 요청시 title값은 필수")
    void test2() throws Exception {
        mockMvc.perform(post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
//                .content("{\"title\":\"\", \"content\":\"내용입니다..\"}"))
                .content("{\"title\":null, \"content\":\"내용입니다..\"}"))
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
    void test3() throws Exception {
        // when
        mockMvc.perform(post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\":\"제목\", \"content\":\"내용입니다..\"}"))
                .andExpect(status().isOk())
                .andDo(print());

        // then
        Assertions.assertEquals(1L, postRepository.count());

        // 저장이 제대로 되었는지 검증
        Post post = postRepository.findAll().get(0);
        assertEquals("제목", post.getTitle());
        assertEquals("내용입니다..", post.getContent());


        /**
         *     -> 각 테스트들은 다른 테스트에 영향을 끼치지 않아야 함
         *        테스트를 각각 실행할 때는 문제가 없지만 전체를 한 번에 실행하면 test1()에서 성공케이스가 나오므로
         *       test3()에서의 카운트는 2가 됨
         *        각 메서드들을 실행하기 전에 깨끗한 상태에서 테스트를 수행할 수 있는 메서드를 만드는 것이 좋음
         */
    }

}