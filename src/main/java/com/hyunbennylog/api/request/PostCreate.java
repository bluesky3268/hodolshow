package com.hyunbennylog.api.request;

import com.hyunbennylog.api.domain.Post;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class PostCreate {

    @NotBlank(message = "{postCreate.title.notNull}")
    private String title;

    @NotBlank(message = "{postCreate.content.notNull}")
    private String content;

    public Post toEntity() {
        return Post.builder()
                .title(title)
                .content(content)
                .build();
    }

}
