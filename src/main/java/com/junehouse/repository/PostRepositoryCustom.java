package com.junehouse.repository;

import com.junehouse.domain.Post;
import com.junehouse.request.PostSearch;

import java.util.List;

public interface PostRepositoryCustom {

    List<Post> getList(PostSearch postSearch);
}
