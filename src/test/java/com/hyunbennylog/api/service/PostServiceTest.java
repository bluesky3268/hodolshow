package com.hyunbennylog.api.service;

import com.hyunbennylog.api.domain.Post;
import com.hyunbennylog.api.exception.PostNotFoundException;
import com.hyunbennylog.api.repository.PostRepository;
import com.hyunbennylog.api.request.PostCreate;
import com.hyunbennylog.api.request.PostModification;
import com.hyunbennylog.api.request.PostSearch;
import com.hyunbennylog.api.response.PostResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
    @DisplayName("글 단건 조회 성공")
    void findPostSuccess() {
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
        assertEquals("POST_", response.getTitle());
        assertEquals("POST CONTENT", response.getContent());
    }

    @Test
    @DisplayName("글 단건 조회 실패 - 존재하지 않는 게시글")
    void findPostFail() {
        // given
        Post request = Post.builder()
                .title("POST_TITLE1234567890")
                .content("POST CONTENT")
                .build();

        postRepository.save(request);

        // expected
        assertThrows(PostNotFoundException.class, () ->
                postService.getPost(request.getId() + 1L));

    }

    @Test
    @DisplayName("글 목록 페이징 조회")
    void getPostListWithPaging() {
        // given
        List<Post> requestPosts = IntStream.range(1, 31)
                .mapToObj(i -> Post.builder()
                            .title("제목" + i)
                            .content("게시글 내용 - " + i)
                            .build())
                .collect(Collectors.toList());

        postRepository.saveAll(requestPosts);

//        Pageable pageable = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "id"));

        // when
        List<PostResponse> postList = postService.getPostList( PostSearch.builder()
                                                                .page(1)
                                                                .size(5)
                                                                .build());

        // then
        assertEquals(5, postList.size());
        assertEquals("제목30", postList.get(0).getTitle());
        assertEquals("제목26", postList.get(4).getTitle());
    }

    @Test
    @DisplayName("게시글 제목 수정")
    void modifyPostTitle() {
        // given
        Post post = Post.builder()
                .title("제목원본")
                .content("내용입니다아아아")
                .build();

        postRepository.save(post);

        // when

        PostModification postModi = PostModification.builder()
                .title("제목수정")
                .content("내용입니다아아아")
                .build();

        postService.modify(post.getId(), postModi);

        // then
        Post modifiedPost = postRepository.findById(post.getId()).orElseThrow(() -> new PostNotFoundException());

        System.out.println("modifiedPost : " + modifiedPost.toString());

        assertEquals("제목수정", modifiedPost.getTitle());
    }

    @Test
    @DisplayName("게시글 제목 수정 실패 - 존재하지 않는 게시글")
    void modifyPostTitleFail() {
        // given
        Post post = Post.builder()
                .title("제목원본")
                .content("내용입니다아아아")
                .build();

        postRepository.save(post);

        PostModification postModi = PostModification.builder()
                .title("제목수정")
                .content("내용수정입니다아아아")
                .build();

        // expected
        assertThrows(PostNotFoundException.class, () ->
                postService.modify(post.getId() + 1L, postModi));

    }

    @Test
    @DisplayName("게시글 내용 수정")
    void modifyPostContent() {
        // given
        Post post = Post.builder()
                .title("제목원본")
                .content("내용입니다아아아")
                .build();

        postRepository.save(post);

        // when

        PostModification postModi = PostModification.builder()
//                .title("제목원본")
                .content("내용수정입니다아아아")
                .build();

        postService.modify(post.getId(), postModi);

        // then
        Post modifiedPost = postRepository.findById(post.getId()).orElseThrow(() -> new PostNotFoundException());

        System.out.println("modifiedPost : " + modifiedPost.toString());

        assertEquals("내용수정입니다아아아", modifiedPost.getContent());
    }

    @Test
    @DisplayName("게시글 내용 수정 실패 - 존재하지 않는 게시글")
    void modifyPostContentFail() {
        // given
        Post post = Post.builder()
                .title("제목원본")
                .content("내용입니다아아아")
                .build();

        postRepository.save(post);

        PostModification postModi = PostModification.builder()
//                .title("제목원본")
                .content("내용수정입니다아아아")
                .build();

        // expected
        assertThrows(PostNotFoundException.class, () ->
                postService.modify(post.getId() + 1L, postModi));

    }

    @Test
    @DisplayName("게시글 삭제 성공")
    void deletePostSuccess() {
        // given
        Post post = Post.builder()
                .title("제목원본")
                .content("내용입니다아아아")
                .build();

        postRepository.save(post);

        // when

        postService.delete(post.getId());

        // then
        boolean empty = postRepository.findById(post.getId()).isEmpty();
        assertEquals(true, empty);
        assertEquals(0, postRepository.count());

    }

    @Test
    @DisplayName("게시글 삭제 실패 - 존재하지 않는 게시글")
    void deletePostFail() {
        // given
        Post post = Post.builder()
                .title("제목원본")
                .content("내용입니다아아아")
                .build();

        postRepository.save(post);

        // expected
        assertThrows(PostNotFoundException.class, () ->
                postService.delete(post.getId() + 1L));


    }

}