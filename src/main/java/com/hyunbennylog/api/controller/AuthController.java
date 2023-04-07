package com.hyunbennylog.api.controller;

import com.hyunbennylog.api.request.LoginRequest;
import com.hyunbennylog.api.response.SessionResponse;
import com.hyunbennylog.api.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/auth/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequest request) {
        // 요청받은 id와 비밀번호(json형식)을 데이터 베이스에서 조회한 후 일치할 경우 토큰 리턴
        log.info("request : {}", request.toString());
        String accessToken = authService.login(request);
        ResponseCookie cookie = ResponseCookie.from("HYUNBENNYLOG_SESSION_ID", accessToken)
                .domain("localhost") // TODO : 서버 환경에 따라 분리할 필요 있음
                .path("/")
                .httpOnly(true)
                .secure(false)
                .maxAge(Duration.ofDays(30))
                .sameSite("Strict")
                .build();

        log.info(">>>>> cookie : {}", cookie.toString());

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .build();
    }



}
