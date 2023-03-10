package com.junehouse.controller;

import com.fasterxml.jackson.core.PrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.junehouse.domain.Post;
import com.junehouse.repository.PostRepository;
import com.junehouse.request.PostCreate;
import com.junehouse.service.PostService;
import org.aspectj.lang.annotation.Before;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
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

    // * MockMVC ?????? ?????? ??????
    @BeforeEach
    public void setMockMvc() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print()).build();
    }

    @Test
    @DisplayName("/posts ??????")
    void test() throws Exception {
        //given
        PostCreate request = PostCreate.builder()
                .title("???????????????.")
                .content("???????????????.")
                .build();

        String json = objectMapper.writeValueAsString(request);

        // expected
        mockMvc.perform(
                        post("/posts")
                                .contentType(APPLICATION_JSON)
                                .content(json)
                        /*.contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("title", "??? ?????? ?????????")
                        .param("content", "??? ?????? ?????????")*/
                )
                .andExpect(status().isOk())
                .andExpect(content().string(""))
                .andDo(print());
    }

    @Test
    @DisplayName("/posts ????????? title??? ??????!")
    void test2() throws Exception {
        //given
        PostCreate request = PostCreate.builder()
                .content("???????????????.")
                .build();

        String json = objectMapper.writeValueAsString(request);

        mockMvc.perform(
                        post("/posts")
                                .contentType(APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("????????? ???????????????."))
                .andDo(print());
    }

    @Test
    @DisplayName("/posts ????????? DB??? ?????? ????????????.")
    void test3() throws Exception {
        //given
        PostCreate request = PostCreate.builder()
                .title("?????? ????????? ???????????? ????????????")
                .content("???????????????.")
                .build();

        String json = objectMapper.writeValueAsString(request);

        //when ??????
        mockMvc.perform(
                        post("/posts")
                                .contentType(APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(status().isOk())
                .andDo(print());
        //then ??? ?????????
        assertEquals(1L, postRepository.count());
        Post findPost = postRepository.findAll().get(0);
        assertEquals("?????? ????????? ???????????? ????????????", findPost.getTitle());
        assertEquals("???????????????.", findPost.getContent());
    }

    @Test
    @DisplayName("??? 1??? ??????")
    void test4() throws Exception {
        //given
        Post post = Post.builder()
                .title("?????????")
                .content("????????????1")
                .build();

        postRepository.save(post);

        //when + then
        mockMvc.perform(
                        get("/posts/{postId}", post.getId())
                                .contentType(APPLICATION_JSON)
                )
                .andExpect(jsonPath("$.id").value(post.getId()))
                .andExpect(jsonPath("$.title").value("?????????"))
                .andExpect(jsonPath("$.content").value("????????????1"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}