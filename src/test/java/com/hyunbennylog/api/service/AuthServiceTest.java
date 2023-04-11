package com.hyunbennylog.api.service;

import com.hyunbennylog.api.crypto.ScryptPasswordEncoder;
import com.hyunbennylog.api.domain.User;
import com.hyunbennylog.api.exception.AlreadyUserExistException;
import com.hyunbennylog.api.exception.InvalidLoginInfoException;
import com.hyunbennylog.api.exception.UserNotFoundException;
import com.hyunbennylog.api.repository.UserRepository;
import com.hyunbennylog.api.request.LoginRequest;
import com.hyunbennylog.api.request.SignUpRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
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
        String password = "12341234";
        SignUpRequest request = SignUpRequest.builder()
                .email("hyunbenny@mail.com")
                .name("hyunbenny")
                .password(password)
                .build();

        // when
        authService.signUp(request);

        // then
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new UserNotFoundException());
        ScryptPasswordEncoder encoder = new ScryptPasswordEncoder();
        String encryptedPassword = encoder.encrypt(password);

        assertEquals(1, userRepository.count());
        assertEquals("hyunbenny@mail.com", user.getEmail());
        assertEquals("hyunbenny", user.getName());
        assertEquals("12341234", user.getPassword());

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

    @Test
    @DisplayName("로그인 성공")
    void login_success() {
        // given
        User user = User.builder()
                .email("hyunbenny@mail.com")
                .name("hyunbenny")
                .password("1234")
                .build();
        userRepository.save(user);

        LoginRequest loginRequest = LoginRequest.builder()
                .email("hyunbenny@mail.com")
                .password("1234")
                .build();

        // when
        Long userId = authService.login(loginRequest);

        // then
        assertNotNull(userId);
    }

    @Test
    @DisplayName("비밀번호가 다른 경우 로그인 실패")
    void login_fail() {
        // given
        ScryptPasswordEncoder encoder = new ScryptPasswordEncoder();
        User user = User.builder()
                .email("hyunbenny@mail.com")
                .name("hyunbenny")
                .password(encoder.encrypt("1234"))
                .build();
        userRepository.save(user);

        LoginRequest loginRequest = LoginRequest.builder()
                .email("hyunbenny@mail.com")
                .password("123456")
                .build();

        // expected
        assertThrows(InvalidLoginInfoException.class, () -> authService.login(loginRequest));

    }

}