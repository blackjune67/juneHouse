package com.junehouse.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.junehouse.domain.Post;
import com.junehouse.repository.PostRepository;
import com.junehouse.request.PostCreate;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    void clean() {
        postRepository.deleteAll();
    }

    // * MockMVC 한글 깨짐 처리
    @BeforeEach
    public void setMockMvc() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print()).build();
    }

    @Test
    @DisplayName("/posts 요청")
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
    void test3() throws Exception {
        //given
        PostCreate request = PostCreate.builder()
                .title("지각 더럽게 많이하는 여자친구")
                .content("내용입니다.")
                .build();

        String json = objectMapper.writeValueAsString(request);

        //when 언제
        mockMvc.perform(
                        post("/posts")
                                .contentType(APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(status().isOk())
                .andDo(print());
        //then 그 다음에
        assertEquals(1L, postRepository.count());
        Post findPost = postRepository.findAll().get(0);
        assertEquals("지각 더럽게 많이하는 여자친구", findPost.getTitle());
        assertEquals("내용입니다.", findPost.getContent());
    }

    @Test
    @DisplayName("글 1개 조회")
    void test4() throws Exception {
        //given
        Post post = Post.builder()
                .title("1234567891012345")
                .content("bar")
                .build();
        postRepository.save(post);

        //when + then
        mockMvc.perform(get("/posts/{postId}", post.getId())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(post.getId()))
                .andExpect(jsonPath("$.title").value("123456789"))
                .andExpect(jsonPath("$.content").value("bar"))
                .andDo(print());
    }

    @Test
    @DisplayName("글 여러개 조회")
    void test5() throws Exception {
        //given
        List<Post> requestPosts = IntStream.range(0, 20)
                .mapToObj(i ->
                        Post.builder()
                                .title("글쓰기 테스트 " + i)
                                .content("재밌는 내용 " + i)
                                .build())
                .collect(Collectors.toList());
        postRepository.saveAll(requestPosts);
        //when + then
        mockMvc.perform(get("/posts?page=1&size=10")
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


}