package com.hyunbennylog.api.request;

import com.hyunbennylog.api.domain.Post;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class PostCreate {

    @NotBlank(message = "{title.notNull}")
    private String title;

    @NotBlank(message = "{content.notNull}")
    private String content;


    @Builder
    public PostCreate(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Post toEntity() {
        return Post.builder()
                .title(title)
                .content(content)
                .build();
    }

    /**
     * 빌더 패턴의 장점
     * 1. 가독성 -> 객체 생성 시 유연하게 생성 가능 
     * 2. 필요한 필드만 사용해서 객체 생성 가능
     *      -> 생성자를 사용하면 생성자를 오버로딩해서 여러개 만들어야 함
     * 3. 객체의 불변성 -> final 키워드 사용
     * 
     */

}
