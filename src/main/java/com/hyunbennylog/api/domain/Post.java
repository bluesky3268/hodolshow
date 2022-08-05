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
    public Post(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    /**
     * Entity에 서비스 정책을 '절대' 넣으면 안됨
     */

    public void modify(PostEditor postEditor) {
        title = postEditor.getTitle() != null ? postEditor.getTitle() : title;
        content = postEditor.getContent() != null ? postEditor.getContent() : content;
    }

    public PostEditor.PostEditorBuilder toEditor() {
        return PostEditor.builder()
                .title(title)
                .content(content);
    }
}
