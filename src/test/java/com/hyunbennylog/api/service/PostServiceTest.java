package com.hyunbennylog.api.service;

import com.hyunbennylog.api.domain.Post;
import com.hyunbennylog.api.repository.PostRepository;
import com.hyunbennylog.api.request.PostCreate;
import com.hyunbennylog.api.response.PostResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;

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
                .title("POST_TITLE1234567890")
                .content("POST CONTENT")
                .build();

        postRepository.save(request);

        // when
        PostResponse response = postService.getPost(request.getId());

        // then
        assertNotNull(response);
        assertEquals("POST TITLE", response.getTitle());
        assertEquals("POST CONTENT", response.getContent());
    }


    @Test
    @DisplayName("글 목록 조회")
    void getPostList() {
        // given
        Post request1 = Post.builder()
                .title("title1")
                .content("content1")
                .build();

        Post request2 = Post.builder()
                .title("title2")
                .content("content2")
                .build();

        postRepository.saveAll(List.of(request1, request2));

        // when
        List<PostResponse> postList = postRepository.findAll().stream()
                .map(post -> new PostResponse().entityToPostResponse(post))
                .collect(Collectors.toList());

        // then
        assertEquals(2, postList.size());
    }
}