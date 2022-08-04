package com.hyunbennylog.api.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@ToString
@Entity
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob
    private String content;

    @Builder
    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

    /**
     * Entity에 서비스 정책을 '절대' 넣으면 안됨
     */
}
