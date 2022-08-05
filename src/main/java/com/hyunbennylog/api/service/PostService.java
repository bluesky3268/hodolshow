package com.hyunbennylog.api.service;

import com.hyunbennylog.api.domain.Post;
import com.hyunbennylog.api.domain.PostEditor;
import com.hyunbennylog.api.repository.PostRepository;
import com.hyunbennylog.api.request.PostCreate;
import com.hyunbennylog.api.request.PostModification;
import com.hyunbennylog.api.request.PostSearch;
import com.hyunbennylog.api.response.PostResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    // 게시글 등록
    public void regist(PostCreate postCreate) {
        postRepository.save(postCreate.toEntity());
    }

    // 게시글 단건 조회
    public PostResponse getPost(Long postId) {
        Post findPost = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당글은 존재하지 않습니다."));

        return new PostResponse().entityToPostResponse(findPost);
    }

    // 게시글 리스트 조회
    public List<PostResponse> getPostList(PostSearch postSearch) {
//        Pageable pageable = PageRequest.of(page, 5, Sort.by(Sort.Direction.DESC, "id"));
//        return postRepository.findAll(pageable).stream().map(post -> new PostResponse().entityToPostResponse(post))
//                .collect(Collectors.toList());

        return postRepository.getList(postSearch).stream()
                .map(post -> new PostResponse().entityToPostResponse(post))
                .collect(Collectors.toList());

    }

    // 게시글 수정
    @Transactional
    public PostResponse modify(Long id, PostModification request) {
        Post findPost = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 글은 존재하지 않습니다."));
        PostEditor.PostEditorBuilder postEditorBuilder = findPost.toEditor();

        PostEditor postEditor = postEditorBuilder.title(request.getTitle())
                .content(request.getContent())
                .build();

        findPost.modify(postEditor);
        /**
         * 수정할 데이터만 보낼건지, 모든 데이터를 다 보낼건지는 클라이언트와 협의해야 함
         */
//        postRepository.save(findPost); // -> save()안하고 @Transactional을 걸어주면 알아서 커밋함

        return new PostResponse().entityToPostResponse(findPost);
    }

    public void delete(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException());

        postRepository.delete(post);
    }


}
