package com.junehouse.service;

import com.junehouse.domain.Post;
import com.junehouse.exception.PostNotFound;
import com.junehouse.repository.PostRepository;
import com.junehouse.request.PostCreate;
import com.junehouse.request.PostEdit;
import com.junehouse.request.PostSearch;
import com.junehouse.response.PostResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    /*@Test
    @DisplayName("글 여러개 조회")
    void test3() {
        //given
        postRepository.saveAll(List.of(
                Post.builder()
                        .title("글한개")
                        .content("ㅅㄱㅇ")
                        .build(),
                Post.builder()
                        .title("글두개")
                        .content("ㅎㅇㅎㅇ")
                        .build()
        ));

        //when
        List<PostResponse> posts = postService.getList(1);

        //then 리스트 조회
        assertEquals(2L, posts.size());
    }*/

    @Test
    @DisplayName("글 1page 조회")
    void test3() {
        // select, limit, offset
        //given
        List<Post> requestPosts = IntStream.range(0, 20)
                .mapToObj(i ->
                        Post.builder()
                                .title("글쓰기 테스트 " + i)
                                .content("재밌는 내용 " + i)
                                .build())
                .collect(Collectors.toList());
        postRepository.saveAll(requestPosts);
       /* postRepository.saveAll(List.of(
                Post.builder()
                        .title("글한개")
                        .content("ㅅㄱㅇ")
                        .build(),
                Post.builder()
                        .title("글두개")
                        .content("ㅎㅇㅎㅇ")
                        .build()
        ));*/

//        Pageable pageable = PageRequest.of(0, 5, Sort.Direction.DESC, "id");
        PostSearch postSearch = PostSearch.builder()
                .page(1)
                .size(10)
                .build();

        //when
        List<PostResponse> posts = postService.getList(postSearch);

        //then 리스트 조회
        assertEquals(10L, posts.size());
        assertEquals("글쓰기 테스트 19", posts.get(0).getTitle());
//        assertEquals("글쓰기 테스트 21", posts.get(9).getTitle());

    }

    @Test
    @DisplayName("글 수정")
    void test4() {
        //given
        Post post = Post.builder()
                .title("아이폰")
                .content("애플비젼")
                .build();
        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title("안드로이드")
                .content("안드로이드 비젼")
                .build();

        //when
        postService.edit(post.getId(), postEdit);

        //then
        Post changePost = postRepository.findById(post.getId())
                .orElseThrow(() -> new RuntimeException("글이 존재하지 않습니다. id = " + post.getId()));

//        assertEquals("안드로이드", changePost.getTitle());
        assertEquals("안드로이드 비젼", changePost.getContent());
    }

    @Test
    @DisplayName("글 수정")
    void test5() {
        //given
        Post post = Post.builder()
                .title("아이폰")
                .content("애플")
                .build();
        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title(null)
                .content("샘송")
                .build();

        //when
        postService.edit(post.getId(), postEdit);

        //then
        Post changePost = postRepository.findById(post.getId())
                .orElseThrow(() -> new RuntimeException("글이 존재하지 않습니다. id = " + post.getId()));

//        assertEquals("안드로이드", changePost.getTitle());
        assertEquals("아이폰", changePost.getTitle());
        assertEquals("샘송", changePost.getContent());
    }

    @Test
    @DisplayName("게시글 삭제")
    void test6() {
        //given
        Post post = Post.builder()
                .title("게시글1")
                .content("내용1")
                .build();
        postRepository.save(post);

        //when
        postService.delete(post.getId());

        //then
        assertEquals(0, postRepository.count());
    }

    @Test
    @DisplayName("글 1개 조회")
    void test7() {
        //given
        Post post = Post.builder()
                .title("스타벅스")
                .content("아이스아메리카노")
                .build();
        postRepository.save(post);

        // expected
        assertThrows(PostNotFound.class, () -> {
            postService.get(post.getId() + 1L);
        });
//        assertEquals("존재하지 않는 글입니다.", illegalArgumentException.getMessage());
    }

    @Test
    @DisplayName("게시글 삭제, 존재하지 않는 글 삭제")
    void test8() {
        //given
        Post post = Post.builder()
                .title("게시글1")
                .content("내용1")
                .build();
        postRepository.save(post);

        // expected
        assertThrows(PostNotFound.class, () -> {
            postService.delete(post.getId() + 1L);
        });
    }

    @Test
    @DisplayName("글 수정, 존재하지 않는 게시글")
    void test9() {
        //given
        Post post = Post.builder()
                .title("아이폰")
                .content("애플")
                .build();
        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title(null)
                .content("샘송")
                .build();

        // expected
        assertThrows(PostNotFound.class, () -> {
            postService.edit(post.getId() + 1L, postEdit);
        });
    }
}