package com.hyunbennylog.api.controller;

import com.hyunbennylog.api.domain.User;
import com.hyunbennylog.api.exception.InvalidLoginInfoException;
import com.hyunbennylog.api.exception.InvalidRequestException;
import com.hyunbennylog.api.repository.UserRepository;
import com.hyunbennylog.api.request.LoginRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;

    @PostMapping("/auth/login")
    public User login(@RequestBody LoginRequest request) {
        // 요청받은 id와 비밀번호(json형식)을 데이터 베이스에서 조회한 후 일치할 경우 토큰 리턴
        log.info("request : {}", request.toString());

        User findUser = userRepository.findByEmailAndPassword(request.getEmail(), request.getPassword())
                .orElseThrow(() -> new InvalidLoginInfoException());
        log.info("findUser : {}", findUser.toString());

        return findUser;
    }


}
