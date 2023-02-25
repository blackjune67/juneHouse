package com.junehouse.service;

import com.junehouse.domain.Post;
import com.junehouse.repository.PostRepository;
import com.junehouse.request.PostCreate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public void write(PostCreate postCreate) {
        // * Before
        //Post post = new Post(postCreate.getTitle(), postCreate.getContent());

        // * After
        Post post = Post.builder()
                .title(postCreate.title)
                .content(postCreate.content)
                .build();

        postRepository.save(post);
    }
}