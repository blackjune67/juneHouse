package com.junehouse.controller;

import com.junehouse.domain.Post;
import com.junehouse.repository.PostRepository;
import com.junehouse.service.PostService;
import org.aspectj.lang.annotation.Before;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@WebMvcTest
@SpringBootTest
@AutoConfigureMockMvc
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;

    // * MockMVC 한글 깨짐 처리
    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    void clean() {
        postRepository.deleteAll();
    }

    @BeforeEach
    public void setMockMvc() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print()).build();
    }

    @Test
    @DisplayName("/posts 요청")
    void test() throws Exception {
        mockMvc.perform(
                        post("/posts")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"title\" : \"제목입니다.\", \"content\": \"내용입니다.\"}")
                        /*.contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("title", "글 제목 테스트")
                        .param("content", "글 내용 테스트")*/
                )
                .andExpect(status().isOk())
                .andExpect(content().string("{}"))
                .andDo(print());
    }

    @Test
    @DisplayName("/posts 요청시 title값 필수!")
    void test2() throws Exception {
        mockMvc.perform(
                        post("/posts")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"title\" : null, \"content\": \"내용입니다.\"}")
                        /*.contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("title", "글 제목 테스트")
                        .param("content", "글 내용 테스트")*/
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
//                .andExpect(content().string("hello"))
                .andDo(print());
    }
    @Test
    @DisplayName("/posts 요청시 DB에 값이 저장된다.")
    void test3() throws Exception {
        //when 언제
        mockMvc.perform(
                        post("/posts")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"title\" : \"지각 더럽게 많이하는 여자친구\", \"content\": \"내용입니다.\"}")
                )
                .andExpect(status().isOk())
                .andDo(print());
        //then 그 다음에
        assertEquals(1L, postRepository.count());
        Post findPost = postRepository.findAll().get(0);
        assertEquals("지각 더럽게 많이하는 여자친구", findPost.getTitle());
        assertEquals("내용입니다.", findPost.getContent());
    }
}