package com.hyunbennylog.api.controller;

import com.hyunbennylog.api.config.AppConfig;
import com.hyunbennylog.api.request.LoginRequest;
import com.hyunbennylog.api.response.SessionResponse;
import com.hyunbennylog.api.service.AuthService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKey;
import java.security.Key;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final AppConfig appConfig;


    @PostMapping("/auth/login")
    public SessionResponse login(@RequestBody LoginRequest request) {
        // 요청받은 id와 비밀번호(json형식)을 데이터 베이스에서 조회한 후 일치할 경우 토큰 리턴
        // JWT
        Long userId = authService.login(request);

//        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        SecretKey secretKey = Keys.hmacShaKeyFor(appConfig.getJwt_key());
        String jws = Jwts.builder()
                .setSubject(String.valueOf(userId))
                .signWith(secretKey)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1 * (1000 * 60 * 60 * 24)))
                .compact();

        return new SessionResponse(jws);
    }



}
