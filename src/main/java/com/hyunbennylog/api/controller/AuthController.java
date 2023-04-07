package com.hyunbennylog.api.controller;

import com.hyunbennylog.api.domain.User;
import com.hyunbennylog.api.exception.InvalidLoginInfoException;
import com.hyunbennylog.api.exception.InvalidRequestException;
import com.hyunbennylog.api.repository.UserRepository;
import com.hyunbennylog.api.request.LoginRequest;
import com.hyunbennylog.api.response.SessionResponse;
import com.hyunbennylog.api.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/auth/login")
    public SessionResponse login(@RequestBody LoginRequest request) {
        // 요청받은 id와 비밀번호(json형식)을 데이터 베이스에서 조회한 후 일치할 경우 토큰 리턴
        log.info("request : {}", request.toString());
        String accessToken = authService.login(request);
        return new SessionResponse(accessToken);
    }



}
