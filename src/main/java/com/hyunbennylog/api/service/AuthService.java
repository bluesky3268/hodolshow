package com.hyunbennylog.api.service;

import com.hyunbennylog.api.crypto.PasswordEncoder;
import com.hyunbennylog.api.crypto.ScryptPasswordEncoder;
import com.hyunbennylog.api.domain.User;
import com.hyunbennylog.api.exception.InvalidLoginInfoException;
import com.hyunbennylog.api.exception.AlreadyUserExistException;
import com.hyunbennylog.api.repository.UserRepository;
import com.hyunbennylog.api.request.LoginRequest;
import com.hyunbennylog.api.request.SignUpRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Long login(LoginRequest request) {
        User findUser = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new InvalidLoginInfoException());

        boolean matches = passwordEncoder.matches(request.getPassword(), findUser.getPassword());
        if (!matches) throw new InvalidLoginInfoException();

        return findUser.getId();
    }

    @Transactional
    public void signUp(SignUpRequest request) {
        Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());
        if (optionalUser.isPresent()) throw new AlreadyUserExistException();

        String encryptedPassword = passwordEncoder.encrypt(request.getPassword());
        request.setPassword(encryptedPassword);

        userRepository.save(request.toEntity());
    }
}
