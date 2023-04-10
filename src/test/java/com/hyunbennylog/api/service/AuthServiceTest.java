package com.hyunbennylog.api.service;

import com.hyunbennylog.api.domain.User;
import com.hyunbennylog.api.exception.AlreadyUserExistException;
import com.hyunbennylog.api.exception.UserNotFoundException;
import com.hyunbennylog.api.repository.UserRepository;
import com.hyunbennylog.api.request.SignUpRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest

class AuthServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService authService;

    @BeforeEach
    void clean() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("회원가입 성공")
    void signUp_success() {
        // given
        SignUpRequest request = SignUpRequest.builder()
                .email("hyunbenny@mail.com")
                .name("hyunbenny")
                .password("12341234")
                .build();

        // when
        authService.signUp(request);

        // then
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new UserNotFoundException());

        assertEquals(1, userRepository.count());
        assertEquals("hyunbenny@mail.com", user.getEmail());
        assertEquals("hyunbenny", user.getName());

    }

    @Test
    @DisplayName("회원가입 시 중복된 이메일이 있으면 예외를 반환한다.")
    void signUp_alreadyExistEmail() {
        // given
        User user = User.builder()
                .email("hyunbenny@mail.com")
                .name("hyunbenny")
                .password("1234")
                .build();
        userRepository.save(user);

        SignUpRequest request = SignUpRequest.builder()
                .email("hyunbenny@mail.com")
                .name("hyunbenny")
                .password("12341234")
                .build();

        // when then
        assertThrows(AlreadyUserExistException.class, () -> authService.signUp(request));

    }

}