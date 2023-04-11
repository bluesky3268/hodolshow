package com.hyunbennylog.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyunbennylog.api.crypto.ScryptPasswordEncoder;
import com.hyunbennylog.api.domain.Session;
import com.hyunbennylog.api.domain.User;
import com.hyunbennylog.api.repository.SessionRepository;
import com.hyunbennylog.api.repository.UserRepository;
import com.hyunbennylog.api.request.LoginRequest;
import com.hyunbennylog.api.request.SignUpRequest;
import com.hyunbennylog.api.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionRepository sessionRepository;

    private ScryptPasswordEncoder encoder = new ScryptPasswordEncoder();


    @BeforeEach
    void clean() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("로그인 성공")
    void login() throws Exception {
        //given
        String password = "1234";
        String encryptedPassword = encoder.encrypt(password);
        userRepository.save(User.builder()
                .email("hyunbenny@mail.com")
                .password(encryptedPassword)
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
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @Transactional
    @DisplayName("로그인 실패")
    void loginAndCreateSession() throws Exception {
        //given
        String password = "1234";
        String encryptedPassword = encoder.encrypt(password);
        User user = User.builder()
                .email("hyunbenny@mail.com")
                .password(encryptedPassword)
                .name("조현빈")
                .createdAt(LocalDateTime.now())
                .build();
        userRepository.save(user);

        LoginRequest request = LoginRequest.builder()
                .email("hyunbenny@mail.com")
                .password("1234555")
                .build();

        String json = objectMapper.writeValueAsString(request);

        // expected
        mockMvc.perform(post("/auth/login")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().is4xxClientError())
                .andDo(print());

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
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.accessToken").isNotEmpty())
                .andExpect(jsonPath("$.accessToken", notNullValue()))
                .andDo(print());
    }

    @Test
    @DisplayName("로그인 후 권한이 필요한 페이지에 접속한다.(/foo)")
    void requestToApi_needAuth_afterLogin() throws Exception {
        // given
        User user = User.builder()
                .email("hyunbenny@mail.com")
                .password("1234")
                .name("조현빈")
                .createdAt(LocalDateTime.now())
                .build();
        Session session = user.addSession();
        userRepository.save(user);


        // expected
        mockMvc.perform(get("/foo")
                        .header("Authorization", session.getAccessToken())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("로그인 후 권한이 필요한 페이지에 검증되지 않은 토큰으로 접속하면 401.(/foo)")
    void requestToApi_needAuth_withWrongAccessToken_afterLogin() throws Exception {
        // given
        String password = "1234";
        String encryptedPassword = encoder.encrypt(password);
        User user = User.builder()
                .email("hyunbenny@mail.com")
                .password(encryptedPassword)
                .name("조현빈")
                .createdAt(LocalDateTime.now())
                .build();
        Session session = user.addSession();
        userRepository.save(user);


        // then
        mockMvc.perform(get("/foo")
                        .header("Authorization", session + "isWrong")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }

    @Test
    @DisplayName("회원가입")
    void signup() throws Exception {
        // given
        SignUpRequest request = SignUpRequest.builder()
                .email("hyunbenny@mail.com")
                .name("hyunbenny")
                .password("12341234")
                .build();

        // when then
        mockMvc.perform(post("/auth/sign-up")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }



}