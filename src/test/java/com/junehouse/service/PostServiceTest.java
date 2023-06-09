package com.junehouse.service;

import com.junehouse.domain.Post;
import com.junehouse.repository.PostRepository;
import com.junehouse.request.PostCreate;
import com.junehouse.response.PostResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void clean() {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("글 작성")
    public void test1() {
        // given
        PostCreate postCreate = PostCreate.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();
        // when
        postService.write(postCreate);

        //then
        assertEquals(1L, postRepository.count());
        Post post = postRepository.findAll().get(0);
        assertEquals("제목입니다.", post.getTitle());
        assertEquals("내용입니다.", post.getContent());
    }

    @Test
    @DisplayName("글 1개 조회")
    void test2() {
        //given
        Post postBuild = Post.builder()
                .title("1234567891011121314")
                .content("10자 이상이라면?")
                .build();
        postRepository.save(postBuild);

        // 클라이언트 요구사항 = title 길이 10제한이라면??

        //when
        PostResponse post = postService.get(postBuild.getId());

        //then null 이면 안된다
        assertNotNull(post);
        assertEquals(1L, postRepository.count());
        assertEquals("1234567891", post.getTitle());
        assertEquals("10자 이상이라면?", post.getContent());
    }

    @Test
    @DisplayName("글 여러개 조회")
    void test3() {
        //given
        Post postBuild = Post.builder()
                .title("글한개")
                .content("ㅅㄱㅇ")
                .build();
        postRepository.save(postBuild);

        Post postBuild2 = Post.builder()
                .title("글두개")
                .content("ㅎㅇㅎㅇ")
                .build();
        postRepository.save(postBuild2);

        // 클라이언트 요구사항 = title 길이 10제한이라면??

        //when
        List<PostResponse> posts = postService.getList();

        //then 리스트 조회
        assertEquals(2L, posts.size());
    }
}