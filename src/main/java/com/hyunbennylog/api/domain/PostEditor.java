package com.hyunbennylog.api.domain;

import lombok.Builder;
import lombok.Getter;

/**
 * PostEditor는 수정할 수 있는 항목에 대해서만 필드로 선언해 줌
 */
@Getter
public class PostEditor {

    private final String title;
    private final String content;

    @Builder
    public PostEditor(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
