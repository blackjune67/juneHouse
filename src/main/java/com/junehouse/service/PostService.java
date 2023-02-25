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

    // * CASE_1 : 저장한 데이터 Entity -> response로 응답하기
    /*public Post write(PostCreate postCreate) {
        // * Before
        //Post post = new Post(postCreate.getTitle(), postCreate.getContent());

        // * After
        Post post = Post.builder()
                .title(postCreate.title)
                .content(postCreate.content)
                .build();

       return postRepository.save(post);
    }*/

    // * CASE_2 : 저장한 데이터의 primary_id -> response로 응답하기
    /*public Long write(PostCreate postCreate) {
        Post post = Post.builder()
                .title(postCreate.title)
                .content(postCreate.content)
                .build();

        postRepository.save(post);
        return post.getId();
    }*/

    // * CASE_3 : 응답 필요 없음 -> 클라이언트에서 모든 POST(글) 데이터 context를 관리함.
    public void write(PostCreate postCreate) {
        Post post = Post.builder()
                .title(postCreate.title)
                .content(postCreate.content)
                .build();
        postRepository.save(post);
    }
}