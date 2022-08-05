package com.hyunbennylog.api.repository;

import com.hyunbennylog.api.domain.Post;
import com.hyunbennylog.api.request.PostSearch;

import java.util.List;

public interface PostRepositoryCustom {

    List<Post> getList(PostSearch postSearch);
}
