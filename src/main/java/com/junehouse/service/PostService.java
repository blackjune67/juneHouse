package com.junehouse.service;

import com.junehouse.domain.Post;
import com.junehouse.domain.PostEditor;
import com.junehouse.exception.PostNotFound;
import com.junehouse.exception.UserNotFound;
import com.junehouse.repository.MemberRepository;
import com.junehouse.repository.PostRepository;
import com.junehouse.request.PostCreate;
import com.junehouse.request.PostEdit;
import com.junehouse.request.PostSearch;
import com.junehouse.response.PostResponse;
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
    private final MemberRepository memberRepository;

    // * CASE_3 : 응답 필요 없음 -> 클라이언트에서 모든 POST(글) 데이터 context를 관리함.
    public void write(Long userId, PostCreate postCreate) {
        var member = memberRepository.findById(userId)
                .orElseThrow(UserNotFound::new);

        Post post = Post.builder()
                .member(member)
                .title(postCreate.title)
                .content(postCreate.content)
                .build();
        postRepository.save(post);
    }
    // * 서비스 정책에 맞는 응답 클래스 분리
    public PostResponse get(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFound::new);

        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .build();
    }
    public List<PostResponse> getList(PostSearch postSearch) {
        return postRepository.getList(postSearch).stream()
                .map(PostResponse::new)
                .collect(Collectors.toList());
    }
    @Transactional
    public PostResponse edit(Long id, PostEdit postEdit) {
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFound::new);

        // * 방법 1
        PostEditor.PostEditorBuilder postEditorBuilder = post.toEditor();

        // * 해당 기능만 사용할 수 있게끔 한다.
        PostEditor postEditor = postEditorBuilder
                .title(postEdit.getTitle())
                .content(postEdit.getContent())
                .build();

        post.edit(postEditor);

        // * responseBody 전달하려고 함.
        return new PostResponse(post);
    }
    public void delete(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFound::new);

        postRepository.delete(post);
    }
}