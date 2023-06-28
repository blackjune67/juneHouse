package com.junehouse.service;

import com.junehouse.domain.Post;
import com.junehouse.domain.PostEditor;
import com.junehouse.repository.PostRepository;
import com.junehouse.request.PostCreate;
import com.junehouse.request.PostEdit;
import com.junehouse.request.PostSearch;
import com.junehouse.response.PostResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

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

    // * 서비스 정책에 맞는 응답 클래스 분리
    public PostResponse get(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다."));

        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .build();
    }

    public List<PostResponse> getList(PostSearch postSearch) {
//        Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "id"));

        return postRepository.getList(postSearch).stream()
                .map(PostResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public PostResponse edit(Long id, PostEdit postEdit) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

        // * 방법 1
        PostEditor.PostEditorBuilder postEditorBuilder = post.toEditor();

        // * 해당 기능만 사용할 수 있게끔 한다.
        PostEditor postEditor = postEditorBuilder
                .title(postEdit.getTitle())
                .content(postEdit.getContent())
                .build();

        post.edit(postEditor);

        // * 방법 2
//        post.edit2(postEdit.getTitle(), postEdit.getContent());
        // * 방법 2 (null 허용 상황)
        /*post.edit2(
                postEdit.getTitle() != null ? postEdit.getTitle() : post.getTitle()
                ,postEdit.getContent() != null ? postEdit.getContent() : post.getContent());*/

        // * responseBody 전달하려고 함.
        return new PostResponse(post);
    }

    public void delete(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

        postRepository.delete(post);
    }
}