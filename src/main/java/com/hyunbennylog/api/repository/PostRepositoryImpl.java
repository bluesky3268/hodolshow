package com.hyunbennylog.api.repository;

import com.hyunbennylog.api.domain.Post;
import com.hyunbennylog.api.domain.QPost;
import com.hyunbennylog.api.request.PostSearch;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Post> getList(PostSearch postSearch) {
        int size = postSearch.getSize();
        return queryFactory.selectFrom(QPost.post)
                .limit(size)
                .offset(postSearch.calOffset())
                .orderBy(QPost.post.id.desc())
                .fetch();
    }
}
