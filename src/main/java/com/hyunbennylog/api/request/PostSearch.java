package com.hyunbennylog.api.request;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import lombok.*;

@Getter
@Setter
@ToString
public class PostSearch {

    private static final int MAX_SIZE = 2000;
    private Integer page;

    private Integer size;

    @Builder
    public PostSearch(Integer page, Integer size) {
        this.page = page == null ? Math.min(1, page) : page;
        this.size = size == null ? 20 : size;
    }

    public long calOffset(){
        return (long) (Math.max(1, page) -1) * Math.min(size, MAX_SIZE);
    }

}
