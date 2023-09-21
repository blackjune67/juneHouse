package com.junehouse.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.junehouse.config.CustomAnnotation;
import com.junehouse.domain.Member;
import com.junehouse.domain.Post;
import com.junehouse.repository.MemberRepository;
import com.junehouse.repository.PostRepository;
import com.junehouse.request.PostCreate;
import com.junehouse.request.PostEdit;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@WebMvcTest
@SpringBootTest
@AutoConfigureMockMvc
class PostControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @AfterEach
    void clean() {
        postRepository.deleteAll();
        memberRepository.deleteAll();
    }

    // * MockMVC 한글 깨짐 처리
    /*@BeforeEach
    public void setMockMvc() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print()).build();
    }*/

    @Test
    @DisplayName("/posts 요청")
//    @WithMockUser(username = "fnffn0607@naver.com", roles = {"ADMIN", "USER"})
    @CustomAnnotation()
    void test() throws Exception {
        //given
        PostCreate request = PostCreate.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        String json = objectMapper.writeValueAsString(request);

        // expected
        mockMvc.perform(
                        post("/posts")
                                .contentType(APPLICATION_JSON)
                                .content(json)
                        /*.contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("title", "글 제목 테스트")
                        .param("content", "글 내용 테스트")*/
                )
                .andExpect(status().isOk())
                .andExpect(content().string(""))
                .andDo(print());
    }

    @Test
    @DisplayName("/posts 요청시 title값 필수!")
    @WithMockUser(username = "fnffn0607@naver.com", roles = {"ADMIN", "USER"})
    void test2() throws Exception {
        //given
        PostCreate request = PostCreate.builder()
                .content("내용입니다.")
                .build();

        String json = objectMapper.writeValueAsString(request);

        mockMvc.perform(
                        post("/posts")
                                .contentType(APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andDo(print());
    }

    @Test
    @DisplayName("/posts 요청시 DB에 값이 저장된다.")
//    @WithMockUser(username = "fnffn0607@naver.com", roles = {"ADMIN", "USER"})
    @CustomAnnotation()
    void test3() throws Exception {
        //given
        PostCreate request = PostCreate.builder()
                .title("제목111")
                .content("내용입니다.")
                .build();

        String json = objectMapper.writeValueAsString(request);

        //when 언제
        mockMvc.perform(
                        post("/posts")
                                .header("authorization", "june")
                                .contentType(APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(status().isOk())
                .andDo(print());

        //then 그 다음에
        assertEquals(1L, postRepository.count());
        Post findPost = postRepository.findAll().get(0);
        assertEquals("제목111", findPost.getTitle());
        assertEquals("내용입니다.", findPost.getContent());
    }

    @Test
    @DisplayName("글 1개 조회")
    void test4() throws Exception {
        //given
        Member member = Member
                .builder()
                .name("최하준")
                .email("fnffn0506@naver.com")
                .password("a1234")
                .build();
        memberRepository.save(member);

        Post post = Post.builder()
                .title("1234567891")
                .content("bar")
                .member(member)
                .build();
        postRepository.save(post);

        //when + then
        mockMvc.perform(get("/posts/{postId}", post.getId())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(post.getId()))
                .andExpect(jsonPath("$.title").value("1234567891"))
                .andExpect(jsonPath("$.content").value("bar"))
                .andDo(print());
    }

    @Test
    @DisplayName("글 여러개 조회")
    @CustomAnnotation
    void test5() throws Exception {
        //given
        Member member = Member.builder()
                .name("최하준")
                .email("fnffn0506@naver.com")
                .password("a1234")
                .build();
        memberRepository.save(member);

        List<Post> requestPosts = IntStream.range(0, 20)
                .mapToObj(i ->
                        Post.builder()
                                .title("글쓰기 테스트 " + i)
                                .content("재밌는 내용 " + i)
                                .member(member)
                                .build())
                .collect(Collectors.toList());

        postRepository.saveAll(requestPosts);
        //when + then
        mockMvc.perform(get("/posts?page=0&size=10")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", Matchers.is(10)))
//                .andExpect(jsonPath("$[0].id").value(30))
                .andExpect(jsonPath("$[0].title").value("글쓰기 테스트 19"))
                .andExpect(jsonPath("$[0].content").value("재밌는 내용 19"))
//                .andExpect(jsonPath("$[1].id").value(post2.getId()))
//                .andExpect(jsonPath("$[1].title").value("제목2"))
//                .andExpect(jsonPath("$[1].content").value("게시글2"))
                .andDo(print());
    }

    @Test
    @DisplayName("글 수정")
    @CustomAnnotation
//    @WithMockUser(username = "fnffn0607@naver.com", roles = {"ADMIN", "USER"})
    void test6() throws Exception {
        Member member = memberRepository.findAll().get(0);

        //given
        Post post = Post.builder()
                .title("아이폰")
                .content("애플")
                .member(member)
                .build();
        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title("안드로이드")
                .content("애플")
                .build();

        //when + then
        mockMvc.perform(patch("/posts/{postId}", post.getId())
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postEdit))
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("글 삭제")
//    @WithMockUser(username = "fnffn0607@naver.com", roles = {"ADMIN", "USER"})
    @CustomAnnotation
    void test7() throws Exception {
        //given
        Member member = memberRepository.findAll().get(0);

        Post post = Post.builder()
                .title("아이폰")
                .content("애플")
                .member(member)
                .build();
        postRepository.save(post);

        //when + then
        mockMvc.perform(delete("/posts/{postId}", post.getId())
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("존재하지 않는 게시글")
    void test8() throws Exception {

        // expected
        mockMvc.perform(get("/posts/{postId}", 1L)
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @DisplayName("존재하지 않는 게시글 수정")
    @WithMockUser(username = "fnffn0607@naver.com", roles = {"ADMIN", "USER"})
    void test9() throws Exception {

        PostEdit postEdit = PostEdit.builder()
                .title("안드로이드")
                .content("애플")
                .build();

        // expected
        mockMvc.perform(patch("/posts/{postId}", 1L)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postEdit)))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @DisplayName("게시글 제목 바보 안됨")
    @WithMockUser(username = "fnffn0607@naver.com", roles = {"ADMIN", "USER"})
    void test10() throws Exception {
        // given
        PostCreate request = PostCreate.builder()
                .title("바보")
                .content("내용입니다.")
                .build();

        String json = objectMapper.writeValueAsString(request);

        // expected
        mockMvc.perform(post("/posts")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
}