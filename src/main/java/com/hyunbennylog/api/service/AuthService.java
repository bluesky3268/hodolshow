package com.hyunbennylog.api.service;

import com.hyunbennylog.api.domain.Session;
import com.hyunbennylog.api.domain.User;
import com.hyunbennylog.api.exception.InvalidLoginInfoException;
import com.hyunbennylog.api.repository.UserRepository;
import com.hyunbennylog.api.request.LoginRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    @Transactional
    public String login(LoginRequest request) {
        User findUser = userRepository.findByEmailAndPassword(request.getEmail(), request.getPassword())
                .orElseThrow(() -> new InvalidLoginInfoException());

        // TODO : Bcrypt Scrypt

        Session session = findUser.addSession();
        return session.getAccessToken();
    }

}
