package com.hyunbennylog.api.service;

import com.hyunbennylog.api.domain.Post;
import com.hyunbennylog.api.repository.PostRepository;
import com.hyunbennylog.api.request.PostCreate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void clean() {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName(value="게시글 작성")
    void postRegist() {
        // given
        PostCreate postCreate = PostCreate.builder()
                .title("제목")
                .content("내용임")
                .build();

        // when
        postService.regist(postCreate);

        // then
        Assertions.assertEquals(1L, postRepository.count());

        Post post = postRepository.findAll().get(0);
        assertEquals("제목", post.getTitle());
        assertEquals("내용임", post.getContent());
    }


    @Test
    @DisplayName("글 단건 조회")
    void findPost() {
        // given
        Post request = Post.builder()
                .title("POST TITLE")
                .content("POST CONTENT")
                .build();

        postRepository.save(request);

        Long postId = 1L;

        // when
        Post post = postService.findPost(request.getId());

        // then
        assertNotNull(post);
        assertEquals("POST TITLE", request.getTitle());
        assertEquals("POST CONTENT", request.getContent());
    }

}