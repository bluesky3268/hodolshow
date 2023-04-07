package com.hyunbennylog.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyunbennylog.api.domain.User;
import com.hyunbennylog.api.repository.SessionRepository;
import com.hyunbennylog.api.repository.UserRepository;
import com.hyunbennylog.api.request.LoginRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @BeforeEach
    void clean() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("로그인 성공")
    void login() throws Exception {
        //given
        userRepository.save(User.builder()
                .email("hyunbenny@mail.com")
                .password("1234")
                .name("조현빈")
                .createdAt(LocalDateTime.now())
                .build());

        LoginRequest request = LoginRequest.builder()
                .email("hyunbenny@mail.com")
                .password("1234")
                .build();

        String json = objectMapper.writeValueAsString(request);

        // expected
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @Transactional
    @DisplayName("로그인 성공 후 세션 생성")
    void loginAndCreateSession() throws Exception {
        //given
        User user = User.builder()
                .email("hyunbenny@mail.com")
                .password("1234")
                .name("조현빈")
                .createdAt(LocalDateTime.now())
                .build();
        userRepository.save(user);

        LoginRequest request = LoginRequest.builder()
                .email("hyunbenny@mail.com")
                .password("1234")
                .build();

        String json = objectMapper.writeValueAsString(request);

        // expected
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andDo(print());

        assertEquals(1L, user.getSessions().size());
    }

    @Test
    @DisplayName("로그인 성공 후 Session 응답")
    void loginAndReturnSessionResponse() throws Exception {
        //given
        User user = User.builder()
                .email("hyunbenny@mail.com")
                .password("1234")
                .name("조현빈")
                .createdAt(LocalDateTime.now())
                .build();
        userRepository.save(user);

        LoginRequest request = LoginRequest.builder()
                .email("hyunbenny@mail.com")
                .password("1234")
                .build();

        String json = objectMapper.writeValueAsString(request);

        // expected
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.accessToken").isNotEmpty())
                .andExpect(jsonPath("$.accessToken").isNotEmpty())
                .andDo(print());
    }


}