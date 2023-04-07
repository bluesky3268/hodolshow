package com.hyunbennylog.api.config.data;

import lombok.Getter;

@Getter
public class UserSession {

    public final Long id;

    public UserSession(Long id) {
        this.id = id;
    }
}
