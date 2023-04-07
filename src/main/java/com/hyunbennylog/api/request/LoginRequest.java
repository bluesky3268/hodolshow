package com.hyunbennylog.api.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class LoginRequest {
    @NotBlank(message = "이메일을 입력해주세요")
    private String password;
    @NotBlank(message = "비밀번호를 입력해주세요")
    private String email;

    @Builder
    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginRequest{" +
                "password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
