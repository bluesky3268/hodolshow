package com.hyunbennylog.api.service;

import com.hyunbennylog.api.domain.Post;
import com.hyunbennylog.api.repository.PostRepository;
import com.hyunbennylog.api.request.PostCreate;
import com.hyunbennylog.api.response.PostResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public void regist(PostCreate postCreate) {
        postRepository.save(postCreate.toEntity());
    }

    public PostResponse getPost(Long postId) {
        Post findPost = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당글은 존재하지 않습니다."));

        return new PostResponse().entityToPostResponse(findPost);
    }

    public List<PostResponse> getPostList(Pageable pageable) {
//        Pageable pageable = PageRequest.of(page, 5, Sort.by(Sort.Direction.DESC, "id"));

        return postRepository.findAll(pageable).stream().map(post -> new PostResponse().entityToPostResponse(post))
                .collect(Collectors.toList());

    }

}
