package com.hyunbennylog.api.request;

import com.hyunbennylog.api.domain.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class PostModification {

    @NotBlank(message = "{title.notNull}")
    private String title;

    @NotBlank(message = "{content.notNull}")
    private String content;


    @Builder
    public PostModification(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Post toEntity(Long id) {
        return Post.builder()
                .id(id)
                .title(title)
                .content(content)
                .build();
    }

    @Override
    public String toString() {
        return "PostModification{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
